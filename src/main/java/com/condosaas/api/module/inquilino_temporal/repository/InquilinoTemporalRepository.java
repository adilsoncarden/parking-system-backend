package com.condosaas.api.module.inquilino_temporal.repository;

import com.condosaas.api.module.inquilino_temporal.model.InquilinoTemporal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InquilinoTemporalRepository extends JpaRepository<InquilinoTemporal, Long> {

    List<InquilinoTemporal> findByEstacionamientoId(Long estacionamientoId);

    List<InquilinoTemporal> findByEstacionamiento_Zona_Condominio_Id(Long condominioId);
}
