package com.condosaas.api.module.torre.repository;

import com.condosaas.api.module.torre.model.Torre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TorreRepository extends JpaRepository<Torre, Long> {

    List<Torre> findByCondominioId(Long condominioId);
}