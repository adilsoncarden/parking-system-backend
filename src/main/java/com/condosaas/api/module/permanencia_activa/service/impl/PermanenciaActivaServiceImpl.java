package com.condosaas.api.module.permanencia_activa.service.impl;

import com.condosaas.api.module.permanencia_activa.dto.*;
import com.condosaas.api.module.permanencia_activa.model.*;
import com.condosaas.api.module.permanencia_activa.repository.PermanenciaActivaRepository;
import com.condosaas.api.module.permanencia_activa.service.PermanenciaActivaService;
import com.condosaas.api.module.vehiculo.model.Vehiculo;
import com.condosaas.api.module.vehiculo.repository.VehiculoRepository;
import com.condosaas.api.module.log_acceso_vehicular.model.LogAccesoVehicular;
import com.condosaas.api.module.log_acceso_vehicular.repository.LogAccesoVehicularRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PermanenciaActivaServiceImpl implements PermanenciaActivaService {

    private final PermanenciaActivaRepository repository;
    private final VehiculoRepository vehiculoRepository;
    private final LogAccesoVehicularRepository logRepository;

    @Override
    public PermanenciaActivaResponseDTO create(PermanenciaActivaRequestDTO dto) {

        Vehiculo vehiculo = vehiculoRepository.findById(dto.getVehiculoId())
                .orElseThrow(() -> new EntityNotFoundException("Vehículo no encontrado"));

        LogAccesoVehicular logEntrada = logRepository.findById(dto.getLogEntradaId())
                .orElseThrow(() -> new EntityNotFoundException("Log entrada no encontrado"));

        LogAccesoVehicular logSalida = null;
        if (dto.getLogSalidaId() != null) {
            logSalida = logRepository.findById(dto.getLogSalidaId())
                    .orElseThrow(() -> new EntityNotFoundException("Log salida no encontrado"));
        }

        PermanenciaActiva entity = PermanenciaActiva.builder()
                .fechaEntrada(dto.getFechaEntrada())
                .fechaSalida(dto.getFechaSalida())
                .estado(dto.getEstado())
                .vehiculo(vehiculo)
                .logEntrada(logEntrada)
                .logSalida(logSalida)
                .build();

        return mapToDTO(repository.save(entity));
    }

    @Override
    public PermanenciaActivaResponseDTO getById(Long id) {
        PermanenciaActiva entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Permanencia no encontrada"));

        return mapToDTO(entity);
    }

    @Override
    public List<PermanenciaActivaResponseDTO> getAll(Long vehiculoId) {

        List<PermanenciaActiva> lista;

        if (vehiculoId != null) {
            lista = repository.findByVehiculoId(vehiculoId);
        } else {
            lista = repository.findAll();
        }

        return lista.stream().map(this::mapToDTO).toList();
    }

    @Override
    public PermanenciaActivaResponseDTO update(Long id, PermanenciaActivaRequestDTO dto) {

        PermanenciaActiva entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Permanencia no encontrada"));

        Vehiculo vehiculo = vehiculoRepository.findById(dto.getVehiculoId())
                .orElseThrow(() -> new EntityNotFoundException("Vehículo no encontrado"));

        LogAccesoVehicular logEntrada = logRepository.findById(dto.getLogEntradaId())
                .orElseThrow(() -> new EntityNotFoundException("Log entrada no encontrado"));

        LogAccesoVehicular logSalida = null;
        if (dto.getLogSalidaId() != null) {
            logSalida = logRepository.findById(dto.getLogSalidaId())
                    .orElseThrow(() -> new EntityNotFoundException("Log salida no encontrado"));
        }

        entity.setFechaEntrada(dto.getFechaEntrada());
        entity.setFechaSalida(dto.getFechaSalida());
        entity.setEstado(dto.getEstado());
        entity.setVehiculo(vehiculo);
        entity.setLogEntrada(logEntrada);
        entity.setLogSalida(logSalida);

        return mapToDTO(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Permanencia no encontrada");
        }
        repository.deleteById(id);
    }

    private PermanenciaActivaResponseDTO mapToDTO(PermanenciaActiva entity) {
        return PermanenciaActivaResponseDTO.builder()
                .id(entity.getId())
                .fechaEntrada(entity.getFechaEntrada())
                .fechaSalida(entity.getFechaSalida())
                .estado(entity.getEstado())
                .vehiculoId(entity.getVehiculo().getId())
                .placa(entity.getVehiculo().getPlaca())
                .logEntradaId(entity.getLogEntrada().getId())
                .logSalidaId(entity.getLogSalida() != null ? entity.getLogSalida().getId() : null)
                .build();
    }
}