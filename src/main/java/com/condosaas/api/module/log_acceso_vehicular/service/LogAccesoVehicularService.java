package com.condosaas.api.module.log_acceso_vehicular.service;

import com.condosaas.api.module.log_acceso_vehicular.dto.*;

import java.util.List;

public interface LogAccesoVehicularService {

    LogAccesoVehicularResponseDTO create(LogAccesoVehicularRequestDTO dto);

    LogAccesoVehicularResponseDTO getById(Long id);

    List<LogAccesoVehicularResponseDTO> getAll(Long vehiculoId, Long paseInvitadoId);

    void delete(Long id);
}