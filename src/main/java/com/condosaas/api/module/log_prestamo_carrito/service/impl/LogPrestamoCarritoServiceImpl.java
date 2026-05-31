package com.condosaas.api.module.log_prestamo_carrito.service.impl;

import com.condosaas.api.config.CarritoPenalizacionProperties;
import com.condosaas.api.exception.BusinessRuleException;
import com.condosaas.api.module.carrito_carga.model.CarritoCarga;
import com.condosaas.api.module.carrito_carga.model.EstadoCarrito;
import com.condosaas.api.module.carrito_carga.repository.CarritoCargaRepository;
import com.condosaas.api.module.log_prestamo_carrito.dto.*;
import com.condosaas.api.module.log_prestamo_carrito.model.*;
import com.condosaas.api.module.log_prestamo_carrito.repository.LogPrestamoCarritoRepository;
import com.condosaas.api.module.log_prestamo_carrito.service.LogPrestamoCarritoService;
import com.condosaas.api.module.usuario.model.Usuario;
import com.condosaas.api.module.usuario.repository.UsuarioRepository;
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
    private final CarritoPenalizacionProperties penalizacionProperties;

    @Override
    public LogPrestamoCarritoResponseDTO create(LogPrestamoCarritoRequestDTO dto) {
        CarritoCarga carrito = carritoRepository.findById(dto.getCarritoId())
                .orElseThrow(() -> new EntityNotFoundException("Carrito no encontrado"));

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
        return mapToDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LogPrestamoCarritoResponseDTO> getAll(Long carritoId, Long usuarioId) {
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

        if (entity.getEstado() == EstadoPrestamo.ACTIVO) {
            throw new BusinessRuleException("Use el endpoint de devolución para préstamos activos");
        }

        CarritoCarga carrito = carritoRepository.findById(dto.getCarritoId())
                .orElseThrow(() -> new EntityNotFoundException("Carrito no encontrado"));

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
    public LogPrestamoCarritoResponseDTO registrarDevolucion(Long id) {
        LogPrestamoCarrito entity = repository.findByIdWithRelations(id)
                .orElseThrow(() -> new EntityNotFoundException("Préstamo no encontrado"));

        if (entity.getEstado() != EstadoPrestamo.ACTIVO) {
            throw new BusinessRuleException("El préstamo no está activo");
        }

        LocalDateTime fin = LocalDateTime.now();
        long minutosUsados = Math.max(0, Duration.between(entity.getFechaInicio(), fin).toMinutes());
        int limite = entity.getTiempoLimiteMinutos() != null
                ? entity.getTiempoLimiteMinutos()
                : penalizacionProperties.getTiempoLimiteMinutos();

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
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Préstamo no encontrado");
        }
        repository.deleteById(id);
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
                .build();
    }
}
