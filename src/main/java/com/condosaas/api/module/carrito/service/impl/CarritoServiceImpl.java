package com.condosaas.api.module.carrito.service.impl;

import com.condosaas.api.exception.ResourceNotFoundException;
import com.condosaas.api.module.acceso.model.EntradaSalida;
import com.condosaas.api.module.acceso.repository.EntradaSalidaRepository;
import com.condosaas.api.module.apartamento.model.Apartamento;
import com.condosaas.api.module.apartamento.repository.ApartamentoRepository;
import com.condosaas.api.module.enums.EstadoPrestamo;
import com.condosaas.api.module.carrito.dto.*;
import com.condosaas.api.module.carrito.model.Carrito;
import com.condosaas.api.module.carrito.model.Prestamo;
import com.condosaas.api.module.carrito.repository.CarritoRepository;
import com.condosaas.api.module.carrito.repository.PrestamoRepository;
import com.condosaas.api.module.carrito.service.CarritoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class CarritoServiceImpl implements CarritoService {
    private static final long LIMITE_MINUTOS = 15;
    private static final double MULTA = 5.0;

    private final CarritoRepository carritoRepository;
    private final PrestamoRepository prestamoRepository;
    private final ApartamentoRepository apartamentoRepository;
    private final EntradaSalidaRepository entradaSalidaRepository;

    public CarritoServiceImpl(CarritoRepository carritoRepository, PrestamoRepository prestamoRepository,
            ApartamentoRepository apartamentoRepository, EntradaSalidaRepository entradaSalidaRepository) {
        this.carritoRepository = carritoRepository;
        this.prestamoRepository = prestamoRepository;
        this.apartamentoRepository = apartamentoRepository;
        this.entradaSalidaRepository = entradaSalidaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CarritoResponse> findAll(Long entradaSalidaId, Long condominioId) {
        List<Carrito> list;
        if (entradaSalidaId != null) {
            list = carritoRepository.findByEntradaSalidaId(entradaSalidaId);
        } else if (condominioId != null) {
            list = carritoRepository.findByEntradaSalidaCondominioId(condominioId);
        } else {
            list = carritoRepository.findAll();
        }
        return list.stream().map(this::toCarritoResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CarritoResponse findById(Long id) {
        return toCarritoResponse(getCarrito(id));
    }

    @Override
    public CarritoResponse create(CarritoRequest request) {
        Carrito entity = new Carrito();
        entity.setNombre(request.getNombre());
        entity.setDisponible(true);
        entity.setEntradaSalida(getEntradaSalida(request.getIdEntradaSalida()));
        return toCarritoResponse(carritoRepository.save(entity));
    }

    @Override
    public CarritoResponse update(Long id, CarritoRequest request) {
        Carrito entity = getCarrito(id);
        entity.setNombre(request.getNombre());
        entity.setEntradaSalida(getEntradaSalida(request.getIdEntradaSalida()));
        return toCarritoResponse(carritoRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        carritoRepository.delete(getCarrito(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrestamoResponse> findAllPrestamos() {
        return prestamoRepository.findAll().stream().map(this::toPrestamoResponse).toList();
    }

    @Override
    public PrestamoResponse prestar(PrestamoRequest request) {
        Carrito carrito = getCarrito(request.getIdCarrito());
        if (Boolean.FALSE.equals(carrito.getDisponible())) {
            throw new IllegalStateException("Carrito no disponible");
        }
        Prestamo prestamo = new Prestamo();
        prestamo.setEstado(EstadoPrestamo.ACTIVO);
        prestamo.setHoraInicio(LocalDateTime.now());
        prestamo.setMultado(false);
        prestamo.setMontoMulta(0.0);
        prestamo.setCarrito(carrito);
        prestamo.setApartamento(getApartamento(request.getIdApartamento()));
        prestamo.setEntradaSalida(getEntradaSalida(request.getIdEntradaSalida()));
        carrito.setDisponible(false);
        carritoRepository.save(carrito);
        return toPrestamoResponse(prestamoRepository.save(prestamo));
    }

    @Override
    public PrestamoResponse devolver(Long id) {
        Prestamo prestamo = getPrestamo(id);
        if (prestamo.getEstado() != EstadoPrestamo.ACTIVO) {
            throw new IllegalStateException("Prestamo no activo");
        }
        LocalDateTime fin = LocalDateTime.now();
        prestamo.setHoraFin(fin);
        long minutos = Duration.between(prestamo.getHoraInicio(), fin).toMinutes();
        if (minutos > LIMITE_MINUTOS) {
            prestamo.setMultado(true);
            prestamo.setMontoMulta(MULTA);
            prestamo.setEstado(EstadoPrestamo.MULTADO);
        } else {
            prestamo.setEstado(EstadoPrestamo.DEVUELTO);
        }
        Carrito carrito = prestamo.getCarrito();
        if (carrito != null) {
            carrito.setDisponible(true);
            carritoRepository.save(carrito);
        }
        return toPrestamoResponse(prestamoRepository.save(prestamo));
    }

    private Carrito getCarrito(Long id) {
        return carritoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Carrito no encontrado: " + id));
    }

    private Prestamo getPrestamo(Long id) {
        return prestamoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prestamo no encontrado: " + id));
    }

    private Apartamento getApartamento(Long id) {
        return apartamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Apartamento no encontrado: " + id));
    }

    private EntradaSalida getEntradaSalida(Long id) {
        return entradaSalidaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("EntradaSalida no encontrada: " + id));
    }

    private CarritoResponse toCarritoResponse(Carrito entity) {
        return new CarritoResponse(
                entity.getId(),
                entity.getDisponible(),
                entity.getNombre(),
                entity.getEntradaSalida() != null ? entity.getEntradaSalida().getId() : null);
    }

    private PrestamoResponse toPrestamoResponse(Prestamo entity) {
        return new PrestamoResponse(
                entity.getId(),
                entity.getEstado(),
                entity.getHoraInicio(),
                entity.getHoraFin(),
                entity.getMontoMulta(),
                entity.getMultado(),
                entity.getApartamento() != null ? entity.getApartamento().getId() : null,
                entity.getCarrito() != null ? entity.getCarrito().getId() : null,
                entity.getEntradaSalida() != null ? entity.getEntradaSalida().getId() : null);
    }
}
