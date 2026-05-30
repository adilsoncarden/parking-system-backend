package com.condosaas.api.module.permanencia_activa.repository;

import com.condosaas.api.module.permanencia_activa.model.PermanenciaActiva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PermanenciaActivaRepository extends JpaRepository<PermanenciaActiva, Long> {

    List<PermanenciaActiva> findByVehiculoId(Long vehiculoId);

    Optional<PermanenciaActiva> findByVehiculoIdAndEstado(Long vehiculoId, String estado);
}