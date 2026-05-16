package com.condosaas.api.apartamento.repository;

import com.condosaas.api.apartamento.entity.Apartamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApartamentoRepository extends JpaRepository<Apartamento, Long> {
    
    List<Apartamento> findByPisoId(Long id);
}