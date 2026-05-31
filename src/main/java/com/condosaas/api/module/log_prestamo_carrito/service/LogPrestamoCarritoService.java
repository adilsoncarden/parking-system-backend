package com.condosaas.api.module.log_prestamo_carrito.service;

import com.condosaas.api.module.log_prestamo_carrito.dto.*;

import java.util.List;

public interface LogPrestamoCarritoService {

    LogPrestamoCarritoResponseDTO create(LogPrestamoCarritoRequestDTO dto);

    LogPrestamoCarritoResponseDTO getById(Long id);

    List<LogPrestamoCarritoResponseDTO> getAll(Long carritoId, Long usuarioId);

    LogPrestamoCarritoResponseDTO update(Long id, LogPrestamoCarritoRequestDTO dto);

    void delete(Long id);
}