package com.condosaas.api.module.permanencia_activa.service.impl;

import com.condosaas.api.exception.BusinessRuleException;
import com.condosaas.api.module.estacionamiento.model.Estacionamiento;
import com.condosaas.api.module.estacionamiento.model.EstadoOcupacion;
import com.condosaas.api.module.estacionamiento.repository.EstacionamientoRepository;
import com.condosaas.api.module.permanencia_activa.dto.*;
import com.condosaas.api.module.permanencia_activa.model.*;
import com.condosaas.api.module.permanencia_activa.repository.PermanenciaActivaRepository;
import com.condosaas.api.module.permanencia_activa.service.PermanenciaActivaService;
import com.condosaas.api.module.vehiculo.model.EstadoVehiculo;
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

        Vehiculo vehiculo = vehiculoRepository.findByPlaca(dto.getPlaca()).orElse(null);
        if (vehiculo == null) {
            // Placa no registrada: si viene un nombre de visitante, se registra el carro al
            // vuelo como VISITANTE (sin dueño residente). Si no, error como antes.
            if (dto.getNombreVisitante() != null && !dto.getNombreVisitante().isBlank()) {
                vehiculo = vehiculoRepository.save(Vehiculo.builder()
                        .placa(dto.getPlaca())
                        .estado(EstadoVehiculo.ACTIVO)
                        .tipoOcupante(TipoOcupante.VISITANTE)
                        .build());
            } else {
                throw new EntityNotFoundException(
                        "Vehículo con placa " + dto.getPlaca() + " no encontrado");
            }
        }

        // Evitar doble entrada del mismo vehículo
        repository.findFirstByVehiculoIdAndEstado(vehiculo.getId(), EstadoPermanencia.ACTIVA)
                .ifPresent(p -> {
                    throw new BusinessRuleException(
                            "El vehículo " + dto.getPlaca() + " ya tiene una permanencia activa");
                });

        LocalDateTime ahora = LocalDateTime.now();
        MetodoAcceso metodo = dto.getMetodo() != null ? dto.getMetodo() : MetodoAcceso.MANUAL;

        // Resolver la plaza (si vino) ANTES del log, para dejar traza de la plaza ocupada (spec V6).
        // Respeta la capacidad: 1 auto, o hasta 'capacidad' motos (p. ej. 4) en la misma plaza.
        Estacionamiento plaza = null;
        if (dto.getEstacionamientoId() != null) {
            plaza = estacionamientoRepository.findById(dto.getEstacionamientoId())
                    .orElseThrow(() -> new EntityNotFoundException("Estacionamiento no encontrado"));
            if (plaza.getEstadoOcupacion() == EstadoOcupacion.INACTIVO) {
                throw new BusinessRuleException("La plaza " + plaza.getCodigo() + " no está disponible");
            }
            int ocupacion = plaza.getOcupacionActual() != null ? plaza.getOcupacionActual() : 0;
            int capacidad = plaza.getCapacidad() != null ? plaza.getCapacidad() : 1;
            if (ocupacion >= capacidad) {
                throw new BusinessRuleException(
                        "La plaza " + plaza.getCodigo() + " ya está llena (" + ocupacion + "/" + capacidad + ")");
            }
        }

        // Tipo de ocupante: del dueño (Propietario/Inquilino) o del propio vehículo (Visitante), spec V6.
        TipoOcupante tipoOcupante = vehiculo.getUsuario() != null
                ? vehiculo.getUsuario().getTipoOcupante()
                : vehiculo.getTipoOcupante();

        // Datos del inquilino/visitante (spec V6): se poblan para no-propietarios.
        String datosInquilino = null;
        if (dto.getNombreVisitante() != null && !dto.getNombreVisitante().isBlank()) {
            datosInquilino = dto.getNombreVisitante()
                    + (dto.getDocumentoVisitante() != null && !dto.getDocumentoVisitante().isBlank()
                            ? " (" + dto.getDocumentoVisitante() + ")"
                            : "");
        } else if (tipoOcupante == TipoOcupante.INQUILINO && vehiculo.getUsuario() != null) {
            var u = vehiculo.getUsuario();
            datosInquilino = u.getNombres() + " " + u.getApellidos();
        }

        LogAccesoVehicular logEntrada = logRepository.save(LogAccesoVehicular.builder()
                .tipo(TipoAcceso.ENTRADA)
                .metodo(metodo)
                .fechaHora(ahora)
                .observacion(dto.getObservacion())
                .tipoOcupante(tipoOcupante)
                .datosInquilino(datosInquilino)
                .vehiculo(vehiculo)
                .estacionamiento(plaza)
                .build());

        PermanenciaActiva permanencia = repository.save(PermanenciaActiva.builder()
                .fechaEntrada(ahora)
                .estado(EstadoPermanencia.ACTIVA)
                .vehiculo(vehiculo)
                .estacionamiento(plaza)
                .logEntrada(logEntrada)
                .build());

        // Ocupar la plaza (si vino): suma un cupo y la marca OCUPADO solo cuando se llena.
        if (plaza != null) {
            int ocupacion = (plaza.getOcupacionActual() != null ? plaza.getOcupacionActual() : 0) + 1;
            int capacidad = plaza.getCapacidad() != null ? plaza.getCapacidad() : 1;
            plaza.setOcupacionActual(ocupacion);
            plaza.setEstadoOcupacion(ocupacion >= capacidad ? EstadoOcupacion.OCUPADO : EstadoOcupacion.LIBRE);
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

        // Plaza que ocupaba: preferir la guardada en la permanencia (soporta motos);
        // si es una permanencia antigua sin plaza, caer al lookup por vehiculoActual.
        Estacionamiento plazaOcupada = permanencia.getEstacionamiento() != null
                ? permanencia.getEstacionamiento()
                : estacionamientoRepository.findByVehiculoActualId(vehiculo.getId()).orElse(null);
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

        // Liberar un cupo de la plaza; queda LIBRE mientras no esté llena, y se vacía a null.
        if (plazaOcupada != null) {
            int capacidad = plazaOcupada.getCapacidad() != null ? plazaOcupada.getCapacidad() : 1;
            int ocupacion = Math.max(0,
                    (plazaOcupada.getOcupacionActual() != null ? plazaOcupada.getOcupacionActual() : 1) - 1);
            plazaOcupada.setOcupacionActual(ocupacion);
            plazaOcupada.setEstadoOcupacion(ocupacion >= capacidad ? EstadoOcupacion.OCUPADO : EstadoOcupacion.LIBRE);
            if (ocupacion == 0) {
                plazaOcupada.setVehiculoActual(null);
            }
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