package com.condosaas.api.module.piso.repository;

import com.condosaas.api.module.piso.model.Piso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PisoRepository extends JpaRepository<Piso, Long> {

    List<Piso> findByTorreId(Long torreId);
}