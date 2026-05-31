package com.condosaas.api.module.carrito_carga.repository;

import com.condosaas.api.module.carrito_carga.model.CarritoCarga;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarritoCargaRepository extends JpaRepository<CarritoCarga, Long> {

    Optional<CarritoCarga> findByCodigo(String codigo);

    List<CarritoCarga> findByCondominioId(Long condominioId);
}