package com.condosaas.api.module.apartamento.repository;

import com.condosaas.api.module.apartamento.model.Apartamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApartamentoRepository extends JpaRepository<Apartamento, Long> {

    List<Apartamento> findByPisoId(Long pisoId);
}