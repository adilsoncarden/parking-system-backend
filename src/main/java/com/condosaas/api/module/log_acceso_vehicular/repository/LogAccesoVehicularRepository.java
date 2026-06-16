package com.condosaas.api.module.log_acceso_vehicular.repository;

import com.condosaas.api.module.log_acceso_vehicular.model.LogAccesoVehicular;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface LogAccesoVehicularRepository extends JpaRepository<LogAccesoVehicular, Long> {

    List<LogAccesoVehicular> findByVehiculoId(Long vehiculoId);

    List<LogAccesoVehicular> findByPaseInvitadoId(Long paseInvitadoId);

    long countByFechaHoraGreaterThanEqual(LocalDateTime desde);
}