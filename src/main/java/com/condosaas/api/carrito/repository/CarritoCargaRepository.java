package com.condosaas.api.carrito.repository;

import com.condosaas.api.carrito.entity.CarritoCarga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarritoCargaRepository extends JpaRepository<CarritoCarga, Long> {

    List<CarritoCarga> findByEntradaSalidaId(Long idEntradaSalida);

    List<CarritoCarga> findByEntradaSalidaCondominioId(Long idCondominio);
}