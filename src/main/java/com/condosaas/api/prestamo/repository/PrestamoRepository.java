package com.condosaas.api.prestamo.repository;

import com.condosaas.api.prestamo.entity.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

    List<Prestamo> findByEstado(String estado);

    List<Prestamo> findByCarritoEntradaSalidaCondominioId(Long idCondominio);

    Optional<Prestamo> findByCarritoIdAndEstado(Long idCarrito, String estado);

    List<Prestamo> findByMultadoTrue();
}