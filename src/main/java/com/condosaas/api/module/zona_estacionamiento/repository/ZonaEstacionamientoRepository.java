package com.condosaas.api.module.zona_estacionamiento.repository;

import com.condosaas.api.module.zona_estacionamiento.model.ZonaEstacionamiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ZonaEstacionamientoRepository extends JpaRepository<ZonaEstacionamiento, Long> {

    List<ZonaEstacionamiento> findByCondominioId(Long condominioId);
}