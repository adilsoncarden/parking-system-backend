package com.condosaas.api.module.log_prestamo_carrito.repository;

import com.condosaas.api.module.log_prestamo_carrito.model.LogPrestamoCarrito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogPrestamoCarritoRepository extends JpaRepository<LogPrestamoCarrito, Long> {

    List<LogPrestamoCarrito> findByCarritoId(Long carritoId);

    List<LogPrestamoCarrito> findByUsuarioId(Long usuarioId);
}