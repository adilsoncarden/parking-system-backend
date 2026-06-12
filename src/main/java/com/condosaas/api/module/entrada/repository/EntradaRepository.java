package com.condosaas.api.module.entrada.repository;

import com.condosaas.api.module.entrada.model.Entrada;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EntradaRepository extends JpaRepository<Entrada, Long> {

    List<Entrada> findByCondominioId(Long condominioId);

    long countByCondominioId(Long condominioId);
}
