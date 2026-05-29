package com.condosaas.api.module.carrito.repository;

import com.condosaas.api.module.carrito.model.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    List<Carrito> findByEntradaSalidaId(Long entradaSalidaId);

    List<Carrito> findByEntradaSalidaCondominioId(Long condominioId);
}
