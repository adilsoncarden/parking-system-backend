package com.condosaas.api.module.estacionamiento.repository;

import com.condosaas.api.module.estacionamiento.model.Estacionamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EstacionamientoRepository extends JpaRepository<Estacionamiento, Long> {
    List<Estacionamiento> findByCondominioId(Long condominioId);
}
