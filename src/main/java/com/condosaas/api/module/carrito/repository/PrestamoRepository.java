package com.condosaas.api.module.carrito.repository;

import com.condosaas.api.module.enums.EstadoPrestamo;
import com.condosaas.api.module.carrito.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    List<Prestamo> findByEstado(EstadoPrestamo estado);

    List<Prestamo> findByEntradaSalidaCondominioId(Long condominioId);
}
