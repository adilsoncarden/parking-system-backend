package com.condosaas.api.piso.repository;

import com.condosaas.api.piso.entity.Piso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PisoRepository extends JpaRepository<Piso, Long> {

    List<Piso> findByTorreId(Long idTorre);
}