package com.condosaas.api.module.log_prestamo_carrito.repository;

import com.condosaas.api.module.log_prestamo_carrito.model.EstadoPrestamo;
import com.condosaas.api.module.log_prestamo_carrito.model.LogPrestamoCarrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface LogPrestamoCarritoRepository extends JpaRepository<LogPrestamoCarrito, Long> {

    boolean existsByUsuarioIdAndPenalizadoTrueAndPagadoFalse(Long usuarioId);

    boolean existsByCarritoIdAndEstado(Long carritoId, EstadoPrestamo estado);

    long countByEstado(EstadoPrestamo estado);

    long countByPenalizadoTrueAndPagadoFalse();

    @Query("SELECT COALESCE(SUM(p.montoPenalizacion), 0) FROM LogPrestamoCarrito p WHERE p.penalizado = true AND p.pagado = true")
    BigDecimal sumPenalizacionesPagadas();

    @Query("""
            SELECT p FROM LogPrestamoCarrito p
            JOIN FETCH p.carrito
            JOIN FETCH p.usuario
            """)
    List<LogPrestamoCarrito> findAllWithRelations();

    @Query("""
            SELECT p FROM LogPrestamoCarrito p
            JOIN FETCH p.carrito
            JOIN FETCH p.usuario
            WHERE p.carrito.id = :carritoId
            """)
    List<LogPrestamoCarrito> findByCarritoIdWithRelations(@Param("carritoId") Long carritoId);

    @Query("""
            SELECT p FROM LogPrestamoCarrito p
            JOIN FETCH p.carrito
            JOIN FETCH p.usuario
            WHERE p.usuario.id = :usuarioId
            """)
    List<LogPrestamoCarrito> findByUsuarioIdWithRelations(@Param("usuarioId") Long usuarioId);

    @Query("""
            SELECT p FROM LogPrestamoCarrito p
            JOIN FETCH p.carrito
            JOIN FETCH p.usuario
            WHERE p.id = :id
            """)
    Optional<LogPrestamoCarrito> findByIdWithRelations(@Param("id") Long id);
}
