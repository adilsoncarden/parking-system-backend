package com.condosaas.api.module.log_acceso_vehicular.service.impl;

import com.condosaas.api.module.log_acceso_vehicular.dto.*;
import com.condosaas.api.module.log_acceso_vehicular.model.*;
import com.condosaas.api.module.log_acceso_vehicular.repository.LogAccesoVehicularRepository;
import com.condosaas.api.module.log_acceso_vehicular.service.LogAccesoVehicularService;
import com.condosaas.api.module.estacionamiento.model.Estacionamiento;
import com.condosaas.api.module.estacionamiento.repository.EstacionamientoRepository;
import com.condosaas.api.module.vehiculo.model.Vehiculo;
import com.condosaas.api.module.vehiculo.repository.VehiculoRepository;
import com.condosaas.api.module.pase_invitado.model.PaseInvitado;
import com.condosaas.api.module.pase_invitado.repository.PaseInvitadoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LogAccesoVehicularServiceImpl implements LogAccesoVehicularService {

    private final LogAccesoVehicularRepository repository;
    private final VehiculoRepository vehiculoRepository;
    private final PaseInvitadoRepository paseRepository;
    private final EstacionamientoRepository estacionamientoRepository;

    @Override
    public LogAccesoVehicularResponseDTO create(LogAccesoVehicularRequestDTO dto) {

        Vehiculo vehiculo = vehiculoRepository.findById(dto.getVehiculoId())
                .orElseThrow(() -> new EntityNotFoundException("Vehículo no encontrado"));

        PaseInvitado pase = null;
        if (dto.getPaseInvitadoId() != null) {
            pase = paseRepository.findById(dto.getPaseInvitadoId())
                    .orElseThrow(() -> new EntityNotFoundException("Pase no encontrado"));
        }

        Estacionamiento plaza = null;
        if (dto.getEstacionamientoId() != null) {
            plaza = estacionamientoRepository.findById(dto.getEstacionamientoId())
                    .orElseThrow(() -> new EntityNotFoundException("Estacionamiento no encontrado"));
        }

        LogAccesoVehicular entity = LogAccesoVehicular.builder()
                .tipo(dto.getTipo())
                .metodo(dto.getMetodo())
                .fechaHora(dto.getFechaHora())
                .observacion(dto.getObservacion())
                .tipoOcupante(dto.getTipoOcupante())
                .datosInquilino(dto.getDatosInquilino())
                .vehiculo(vehiculo)
                .estacionamiento(plaza)
                .paseInvitado(pase)
                .build();

        return mapToDTO(repository.save(entity));
    }

    @Override
    public LogAccesoVehicularResponseDTO getById(Long id) {
        LogAccesoVehicular entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Log no encontrado"));

        return mapToDTO(entity);
    }

    @Override
    public List<LogAccesoVehicularResponseDTO> getAll(Long vehiculoId, Long paseInvitadoId) {

        List<LogAccesoVehicular> lista;

        if (vehiculoId != null) {
            lista = repository.findByVehiculoId(vehiculoId);
        } else if (paseInvitadoId != null) {
            lista = repository.findByPaseInvitadoId(paseInvitadoId);
        } else {
            lista = repository.findAll();
        }

        return lista.stream().map(this::mapToDTO).toList();
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Log no encontrado");
        }
        repository.deleteById(id);
    }

    private LogAccesoVehicularResponseDTO mapToDTO(LogAccesoVehicular entity) {
        return LogAccesoVehicularResponseDTO.builder()
                .id(entity.getId())
                .tipo(entity.getTipo())
                .metodo(entity.getMetodo())
                .fechaHora(entity.getFechaHora())
                .observacion(entity.getObservacion())
                .tipoOcupante(entity.getTipoOcupante())
                .datosInquilino(entity.getDatosInquilino())
                .vehiculoId(entity.getVehiculo().getId())
                .placa(entity.getVehiculo().getPlaca())
                .estacionamientoId(entity.getEstacionamiento() != null ? entity.getEstacionamiento().getId() : null)
                .estacionamientoCodigo(entity.getEstacionamiento() != null ? entity.getEstacionamiento().getCodigo() : null)
                .paseInvitadoId(entity.getPaseInvitado() != null ? entity.getPaseInvitado().getId() : null)
                .build();
    }
}