package com.condosaas.api.module.log_prestamo_carrito.service.impl;

import com.condosaas.api.config.CarritoPenalizacionProperties;
import com.condosaas.api.exception.BusinessRuleException;
import com.condosaas.api.module.carrito_carga.model.CarritoCarga;
import com.condosaas.api.module.carrito_carga.model.EstadoCarrito;
import com.condosaas.api.module.carrito_carga.repository.CarritoCargaRepository;
import com.condosaas.api.module.entrada.model.Entrada;
import com.condosaas.api.module.entrada.repository.EntradaRepository;
import com.condosaas.api.module.log_prestamo_carrito.dto.*;
import com.condosaas.api.module.log_prestamo_carrito.model.*;
import com.condosaas.api.module.log_prestamo_carrito.repository.LogPrestamoCarritoRepository;
import com.condosaas.api.module.log_prestamo_carrito.service.LogPrestamoCarritoService;
import com.condosaas.api.module.usuario.model.Usuario;
import com.condosaas.api.module.usuario.repository.UsuarioRepository;
import com.condosaas.api.security.CurrentUser;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LogPrestamoCarritoServiceImpl implements LogPrestamoCarritoService {

    private final LogPrestamoCarritoRepository repository;
    private final CarritoCargaRepository carritoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EntradaRepository entradaRepository;
    private final CarritoPenalizacionProperties penalizacionProperties;
    private final CurrentUser currentUser;

    // Carga una entrada y valida que pertenezca al mismo condominio del carrito.
    private Entrada resolveEntrada(Long entradaId, CarritoCarga carrito) {
        if (entradaId == null) {
            return null;
        }
        Entrada entrada = entradaRepository.findById(entradaId)
                .orElseThrow(() -> new EntityNotFoundException("Entrada no encontrada"));
        if (!entrada.getCondominio().getId().equals(carrito.getCondominio().getId())) {
            throw new BusinessRuleException("La entrada no pertenece al condominio del carrito");
        }
        return entrada;
    }

    @Override
    public LogPrestamoCarritoResponseDTO create(LogPrestamoCarritoRequestDTO dto) {
        CarritoCarga carrito = carritoRepository.findById(dto.getCarritoId())
                .orElseThrow(() -> new EntityNotFoundException("Carrito no encontrado"));
        currentUser.assertCondominio(carrito.getCondominio().getId());

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        if (repository.existsByUsuarioIdAndPenalizadoTrueAndPagadoFalse(usuario.getId())) {
            throw new BusinessRuleException("Tiene penalización pendiente");
        }

        if (carrito.getEstado() != EstadoCarrito.DISPONIBLE) {
            throw new BusinessRuleException("El carrito no está disponible");
        }

        if (repository.existsByCarritoIdAndEstado(carrito.getId(), EstadoPrestamo.ACTIVO)) {
            throw new BusinessRuleException("El carrito ya tiene un préstamo activo");
        }

        // El carrito sale por su entrada fija (si la tiene); si no, por la indicada en el DTO.
        Entrada entradaSalida = carrito.getEntrada() != null
                ? carrito.getEntrada()
                : resolveEntrada(dto.getEntradaSalidaId(), carrito);

        LocalDateTime now = LocalDateTime.now();
        int limite = penalizacionProperties.getTiempoLimiteMinutos();

        LogPrestamoCarrito entity = LogPrestamoCarrito.builder()
                .fechaPrestamo(now)
                .fechaInicio(now)
                .tiempoLimiteMinutos(limite)
                .tiempoExcedidoMinutos(0)
                .montoPenalizacion(BigDecimal.ZERO)
                .penalizado(false)
                .pagado(true)
                .estado(EstadoPrestamo.ACTIVO)
                .carrito(carrito)
                .usuario(usuario)
                .entradaSalida(entradaSalida)
                .build();

        carrito.setEstado(EstadoCarrito.PRESTADO);
        carritoRepository.save(carrito);

        LogPrestamoCarrito saved = repository.save(entity);
        return mapToDTO(repository.findByIdWithRelations(saved.getId()).orElse(saved));
    }

    @Override
    @Transactional(readOnly = true)
    public LogPrestamoCarritoResponseDTO getById(Long id) {
        LogPrestamoCarrito entity = repository.findByIdWithRelations(id)
                .orElseThrow(() -> new EntityNotFoundException("Préstamo no encontrado"));
        currentUser.assertCondominio(entity.getCarrito().getCondominio().getId());
        return mapToDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LogPrestamoCarritoResponseDTO> getAll(Long carritoId, Long usuarioId) {
        if (currentUser.isScoped()) {
            return repository.findByCarritoCondominioIdWithRelations(currentUser.condominioId())
                    .stream().map(this::mapToDTO).toList();
        }
        List<LogPrestamoCarrito> lista;
        if (carritoId != null) {
            lista = repository.findByCarritoIdWithRelations(carritoId);
        } else if (usuarioId != null) {
            lista = repository.findByUsuarioIdWithRelations(usuarioId);
        } else {
            lista = repository.findAllWithRelations();
        }
        return lista.stream().map(this::mapToDTO).toList();
    }

    @Override
    public LogPrestamoCarritoResponseDTO update(Long id, LogPrestamoCarritoRequestDTO dto) {
        LogPrestamoCarrito entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Préstamo no encontrado"));

        // Aislamiento por condominio: no editar préstamos de un condominio ajeno.
        currentUser.assertCondominio(entity.getCarrito().getCondominio().getId());

        if (entity.getEstado() == EstadoPrestamo.ACTIVO) {
            throw new BusinessRuleException("Use el endpoint de devolución para préstamos activos");
        }

        CarritoCarga carrito = carritoRepository.findById(dto.getCarritoId())
                .orElseThrow(() -> new EntityNotFoundException("Carrito no encontrado"));
        currentUser.assertCondominio(carrito.getCondominio().getId());

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        if (dto.getFechaPrestamo() != null) {
            entity.setFechaPrestamo(dto.getFechaPrestamo());
        }
        if (dto.getFechaDevolucion() != null) {
            entity.setFechaDevolucion(dto.getFechaDevolucion());
        }
        if (dto.getEstado() != null) {
            entity.setEstado(dto.getEstado());
        }
        entity.setCarrito(carrito);
        entity.setUsuario(usuario);

        LogPrestamoCarrito saved = repository.save(entity);
        return mapToDTO(repository.findByIdWithRelations(saved.getId()).orElse(saved));
    }

    @Override
    public LogPrestamoCarritoResponseDTO registrarDevolucion(Long id, Long entradaDevolucionId) {
        LogPrestamoCarrito entity = repository.findByIdWithRelations(id)
                .orElseThrow(() -> new EntityNotFoundException("Préstamo no encontrado"));

        if (entity.getEstado() != EstadoPrestamo.ACTIVO) {
            throw new BusinessRuleException("El préstamo no está activo");
        }
        currentUser.assertCondominio(entity.getCarrito().getCondominio().getId());

        // Regla: el carrito se devuelve por la MISMA entrada por la que salió.
        Entrada entradaSalida = entity.getEntradaSalida();
        if (entradaSalida != null) {
            Entrada entradaDev = resolveEntrada(entradaDevolucionId, entity.getCarrito());
            if (entradaDev != null && !entradaDev.getId().equals(entradaSalida.getId())) {
                throw new BusinessRuleException(
                        "El carrito debe devolverse por la entrada \"" + entradaSalida.getNombre() + "\"");
            }
            entity.setEntradaDevolucion(entradaSalida);
        } else {
            entity.setEntradaDevolucion(resolveEntrada(entradaDevolucionId, entity.getCarrito()));
        }

        LocalDateTime fin = LocalDateTime.now();
        long minutosUsados = Math.max(0, Duration.between(entity.getFechaInicio(), fin).toMinutes());
        int limite;
        if (entity.getTiempoLimiteMinutos() != null) {
            limite = entity.getTiempoLimiteMinutos();
        } else {
            limite = penalizacionProperties.getTiempoLimiteMinutos();
        }

        entity.setFechaFin(fin);
        entity.setFechaDevolucion(fin);
        entity.setEstado(EstadoPrestamo.FINALIZADO);

        if (minutosUsados > limite) {
            int excedido = (int) (minutosUsados - limite);
            BigDecimal monto = penalizacionProperties.getTarifaPorMinuto()
                    .multiply(BigDecimal.valueOf(excedido))
                    .setScale(2, RoundingMode.HALF_UP);
            entity.setTiempoExcedidoMinutos(excedido);
            entity.setMontoPenalizacion(monto);
            entity.setPenalizado(true);
            entity.setPagado(false);
        } else {
            entity.setTiempoExcedidoMinutos(0);
            entity.setMontoPenalizacion(BigDecimal.ZERO);
            entity.setPenalizado(false);
            entity.setPagado(true);
        }

        CarritoCarga carrito = entity.getCarrito();
        carrito.setEstado(EstadoCarrito.DISPONIBLE);
        carritoRepository.save(carrito);

        LogPrestamoCarrito saved = repository.save(entity);
        return mapToDTO(saved);
    }

    @Override
    public LogPrestamoCarritoResponseDTO marcarPagado(Long id) {
        LogPrestamoCarrito entity = repository.findByIdWithRelations(id)
                .orElseThrow(() -> new EntityNotFoundException("Préstamo no encontrado"));
        currentUser.assertCondominio(entity.getCarrito().getCondominio().getId());

        if (!Boolean.TRUE.equals(entity.getPenalizado())) {
            throw new BusinessRuleException("El préstamo no tiene penalización");
        }
        if (Boolean.TRUE.equals(entity.getPagado())) {
            throw new BusinessRuleException("La penalización ya fue pagada");
        }

        entity.setPagado(true);
        LogPrestamoCarrito saved = repository.save(entity);
        return mapToDTO(saved);
    }

    @Override
    public void delete(Long id) {
        LogPrestamoCarrito entity = repository.findByIdWithRelations(id)
                .orElseThrow(() -> new EntityNotFoundException("Préstamo no encontrado"));
        currentUser.assertCondominio(entity.getCarrito().getCondominio().getId());
        repository.delete(entity);
    }

    private LogPrestamoCarritoResponseDTO mapToDTO(LogPrestamoCarrito entity) {
        Long minutosUsados = null;
        if (entity.getFechaInicio() != null) {
            LocalDateTime fin = entity.getFechaFin() != null ? entity.getFechaFin() : LocalDateTime.now();
            minutosUsados = Math.max(0, Duration.between(entity.getFechaInicio(), fin).toMinutes());
        }

        String estadoPenalizacion = "SIN_PENALIZACION";
        if (Boolean.TRUE.equals(entity.getPenalizado())) {
            estadoPenalizacion = Boolean.TRUE.equals(entity.getPagado()) ? "PAGADO" : "PENALIZADO";
        }

        return LogPrestamoCarritoResponseDTO.builder()
                .id(entity.getId())
                .fechaPrestamo(entity.getFechaPrestamo())
                .fechaDevolucion(entity.getFechaDevolucion())
                .fechaInicio(entity.getFechaInicio())
                .fechaFin(entity.getFechaFin())
                .tiempoLimiteMinutos(entity.getTiempoLimiteMinutos())
                .tiempoExcedidoMinutos(entity.getTiempoExcedidoMinutos())
                .minutosUsados(minutosUsados)
                .montoPenalizacion(entity.getMontoPenalizacion())
                .penalizado(entity.getPenalizado())
                .pagado(entity.getPagado())
                .estadoPenalizacion(estadoPenalizacion)
                .estado(entity.getEstado())
                .carritoId(entity.getCarrito().getId())
                .codigoCarrito(entity.getCarrito().getCodigo())
                .usuarioId(entity.getUsuario().getId())
                .usuarioNombre(entity.getUsuario().getNombres() + " " + entity.getUsuario().getApellidos())
                .entradaSalidaId(entity.getEntradaSalida() != null ? entity.getEntradaSalida().getId() : null)
                .entradaSalidaNombre(entity.getEntradaSalida() != null ? entity.getEntradaSalida().getNombre() : null)
                .entradaDevolucionId(entity.getEntradaDevolucion() != null ? entity.getEntradaDevolucion().getId() : null)
                .entradaDevolucionNombre(
                        entity.getEntradaDevolucion() != null ? entity.getEntradaDevolucion().getNombre() : null)
                .build();
    }
}
