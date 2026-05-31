package com.condosaas.api.module.detalle_plaza_parkcontrol.repository;

import com.condosaas.api.module.detalle_plaza_parkcontrol.model.DetallePlazaParkControl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DetallePlazaParkControlRepository extends JpaRepository<DetallePlazaParkControl, Long> {

    Optional<DetallePlazaParkControl> findByEstacionamientoId(Long estacionamientoId);
}