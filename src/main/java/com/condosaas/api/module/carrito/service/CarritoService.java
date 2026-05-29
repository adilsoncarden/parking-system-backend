package com.condosaas.api.module.carrito.service;

import com.condosaas.api.module.carrito.dto.*;
import java.util.List;

public interface CarritoService {
    List<CarritoResponse> findAll(Long entradaSalidaId, Long condominioId);

    CarritoResponse findById(Long id);

    CarritoResponse create(CarritoRequest request);

    CarritoResponse update(Long id, CarritoRequest request);

    void delete(Long id);

    List<PrestamoResponse> findAllPrestamos();

    PrestamoResponse prestar(PrestamoRequest request);

    PrestamoResponse devolver(Long id);
}
