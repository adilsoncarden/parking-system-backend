package com.condosaas.api.module.permanencia_activa.service.impl;

import com.condosaas.api.exception.BusinessRuleException;
import com.condosaas.api.module.estacionamiento.model.Estacionamiento;
import com.condosaas.api.module.estacionamiento.model.EstadoOcupacion;
import com.condosaas.api.module.estacionamiento.repository.EstacionamientoRepository;
import com.condosaas.api.module.permanencia_activa.dto.*;
import com.condosaas.api.module.permanencia_activa.model.*;
import com.condosaas.api.module.permanencia_activa.repository.PermanenciaActivaRepository;
import com.condosaas.api.module.permanencia_activa.service.PermanenciaActivaService;
import com.condosaas.api.module.vehiculo.model.Vehiculo;
import com.condosaas.api.module.vehiculo.repository.VehiculoRepository;
import com.condosaas.api.module.log_acceso_vehicular.model.LogAccesoVehicular;
import com.condosaas.api.module.log_acceso_vehicular.model.MetodoAcceso;
import com.condosaas.api.module.log_acceso_vehicular.model.TipoAcceso;
import com.condosaas.api.module.log_acceso_vehicular.repository.LogAccesoVehicularRepository;
import com.condosaas.api.module.usuario.model.TipoOcupante;
import com.condosaas.api.security.CurrentUser;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PermanenciaActivaServiceImpl implements PermanenciaActivaService {

    private final PermanenciaActivaRepository repository;
    private final VehiculoRepository vehiculoRepository;
    private final LogAccesoVehicularRepository logRepository;
    private final EstacionamientoRepository estacionamientoRepository;
    private final CurrentUser currentUser;

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
        } else if (currentUser.isScoped()) {
            // Admin de condominio: solo las permanencias de SU condominio.
            lista = repository.findByCondominioId(currentUser.condominioId());
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

    @Override
    public PermanenciaActivaResponseDTO registrarEntrada(RegistrarEntradaRequestDTO dto) {

        Vehiculo vehiculo = vehiculoRepository.findByPlaca(dto.getPlaca())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Vehículo con placa " + dto.getPlaca() + " no encontrado"));

        // Evitar doble entrada del mismo vehículo
        repository.findFirstByVehiculoIdAndEstado(vehiculo.getId(), EstadoPermanencia.ACTIVA)
                .ifPresent(p -> {
                    throw new BusinessRuleException(
                            "El vehículo " + dto.getPlaca() + " ya tiene una permanencia activa");
                });

        LocalDateTime ahora = LocalDateTime.now();
        MetodoAcceso metodo = dto.getMetodo() != null ? dto.getMetodo() : MetodoAcceso.MANUAL;

        // Resolver la plaza (si vino) ANTES del log, para dejar traza de la plaza ocupada (spec V6).
        Estacionamiento plaza = null;
        if (dto.getEstacionamientoId() != null) {
            plaza = estacionamientoRepository.findById(dto.getEstacionamientoId())
                    .orElseThrow(() -> new EntityNotFoundException("Estacionamiento no encontrado"));
            if (plaza.getEstadoOcupacion() == EstadoOcupacion.OCUPADO) {
                throw new BusinessRuleException("La plaza " + plaza.getCodigo() + " ya está ocupada");
            }
        }

        // Tipo de ocupante según el dueño del vehículo (Propietario / Inquilino), spec V6.
        TipoOcupante tipoOcupante = vehiculo.getUsuario() != null ? vehiculo.getUsuario().getTipoOcupante() : null;

        LogAccesoVehicular logEntrada = logRepository.save(LogAccesoVehicular.builder()
                .tipo(TipoAcceso.ENTRADA)
                .metodo(metodo)
                .fechaHora(ahora)
                .observacion(dto.getObservacion())
                .tipoOcupante(tipoOcupante)
                .vehiculo(vehiculo)
                .estacionamiento(plaza)
                .build());

        PermanenciaActiva permanencia = repository.save(PermanenciaActiva.builder()
                .fechaEntrada(ahora)
                .estado(EstadoPermanencia.ACTIVA)
                .vehiculo(vehiculo)
                .logEntrada(logEntrada)
                .build());

        // Ocupar la plaza indicada (si vino)
        if (plaza != null) {
            plaza.setEstadoOcupacion(EstadoOcupacion.OCUPADO);
            plaza.setVehiculoActual(vehiculo);
            estacionamientoRepository.save(plaza);
        }

        return mapToDTO(permanencia);
    }

    @Override
    public PermanenciaActivaResponseDTO registrarSalida(RegistrarSalidaRequestDTO dto) {

        Vehiculo vehiculo = vehiculoRepository.findByPlaca(dto.getPlaca())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Vehículo con placa " + dto.getPlaca() + " no encontrado"));

        PermanenciaActiva permanencia = repository
                .findFirstByVehiculoIdAndEstado(vehiculo.getId(), EstadoPermanencia.ACTIVA)
                .orElseThrow(() -> new BusinessRuleException(
                        "El vehículo " + dto.getPlaca() + " no tiene una permanencia activa"));

        LocalDateTime ahora = LocalDateTime.now();
        MetodoAcceso metodo = dto.getMetodo() != null ? dto.getMetodo() : MetodoAcceso.MANUAL;

        // Plaza que ocupaba (para dejar traza en el log de salida, spec V6).
        Estacionamiento plazaOcupada = estacionamientoRepository.findByVehiculoActualId(vehiculo.getId()).orElse(null);
        TipoOcupante tipoOcupante = vehiculo.getUsuario() != null ? vehiculo.getUsuario().getTipoOcupante() : null;

        LogAccesoVehicular logSalida = logRepository.save(LogAccesoVehicular.builder()
                .tipo(TipoAcceso.SALIDA)
                .metodo(metodo)
                .fechaHora(ahora)
                .observacion(dto.getObservacion())
                .tipoOcupante(tipoOcupante)
                .vehiculo(vehiculo)
                .estacionamiento(plazaOcupada)
                .build());

        permanencia.setFechaSalida(ahora);
        permanencia.setEstado(EstadoPermanencia.FINALIZADA);
        permanencia.setLogSalida(logSalida);
        repository.save(permanencia);

        // Liberar la plaza que ocupaba (si alguna)
        if (plazaOcupada != null) {
            plazaOcupada.setEstadoOcupacion(EstadoOcupacion.LIBRE);
            plazaOcupada.setVehiculoActual(null);
            estacionamientoRepository.save(plazaOcupada);
        }

        return mapToDTO(permanencia);
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