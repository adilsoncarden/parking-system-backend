package com.condosaas.api.module.estacionamiento.service.impl;

import com.condosaas.api.module.apartamento.model.Apartamento;
import com.condosaas.api.module.apartamento.repository.ApartamentoRepository;
import com.condosaas.api.module.estacionamiento.dto.*;
import com.condosaas.api.module.estacionamiento.model.*;
import com.condosaas.api.module.estacionamiento.repository.EstacionamientoRepository;
import com.condosaas.api.module.estacionamiento.service.EstacionamientoService;
import com.condosaas.api.module.zona_estacionamiento.model.ZonaEstacionamiento;
import com.condosaas.api.module.zona_estacionamiento.repository.ZonaEstacionamientoRepository;
import com.condosaas.api.security.CurrentUser;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EstacionamientoServiceImpl implements EstacionamientoService {

    private final EstacionamientoRepository repository;
    private final ZonaEstacionamientoRepository zonaRepository;
    private final ApartamentoRepository apartamentoRepository;
    private final CurrentUser currentUser;

    // Resuelve el apartamento dueño de la plaza (opcional, spec V6).
    private Apartamento resolveApartamento(Long apartamentoId) {
        if (apartamentoId == null) {
            return null;
        }
        return apartamentoRepository.findById(apartamentoId)
                .orElseThrow(() -> new EntityNotFoundException("Apartamento no encontrado"));
    }

    // Cupo por defecto según el tipo: 1 auto = 4 motos (spec V6).
    private int capacidadPorDefecto(TipoVehiculo tipo) {
        return tipo == TipoVehiculo.MOTO ? 4 : 1;
    }

    @Override
    public EstacionamientoResponseDTO create(EstacionamientoRequestDTO dto) {

        ZonaEstacionamiento zona = zonaRepository.findById(dto.getZonaEstacionamientoId())
                .orElseThrow(() -> new EntityNotFoundException("Zona no encontrada"));

        TipoVehiculo tipo = dto.getTipoVehiculo() != null ? dto.getTipoVehiculo() : TipoVehiculo.AUTO;
        int capacidad = dto.getCapacidad() != null ? dto.getCapacidad() : capacidadPorDefecto(tipo);

        Estacionamiento entity = Estacionamiento.builder()
                .codigo(dto.getCodigo())
                .estadoOcupacion(dto.getEstadoOcupacion())
                .tipoVehiculo(tipo)
                .capacidad(capacidad)
                .zona(zona)
                .apartamento(resolveApartamento(dto.getApartamentoId()))
                .build();

        return mapToDTO(repository.save(entity));
    }

    @Override
    public EstacionamientoResponseDTO getById(Long id) {
        Estacionamiento entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estacionamiento no encontrado"));

        return mapToDTO(entity);
    }

    @Override
    public List<EstacionamientoResponseDTO> getAll(Long zonaId) {

        List<Estacionamiento> lista;

        if (zonaId != null) {
            lista = repository.findByZonaId(zonaId);
        } else if (currentUser.isScoped()) {
            // Admin de condominio: solo el mapa de SU condominio.
            lista = repository.findByZona_Condominio_Id(currentUser.condominioId());
        } else {
            lista = repository.findAll();
        }

        return lista.stream().map(this::mapToDTO).toList();
    }

    @Override
    public EstacionamientoResponseDTO update(Long id, EstacionamientoRequestDTO dto) {

        Estacionamiento entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estacionamiento no encontrado"));

        ZonaEstacionamiento zona = zonaRepository.findById(dto.getZonaEstacionamientoId())
                .orElseThrow(() -> new EntityNotFoundException("Zona no encontrada"));

        entity.setCodigo(dto.getCodigo());
        entity.setEstadoOcupacion(dto.getEstadoOcupacion());
        entity.setZona(zona);
        if (dto.getTipoVehiculo() != null) {
            entity.setTipoVehiculo(dto.getTipoVehiculo());
        }
        if (dto.getCapacidad() != null) {
            entity.setCapacidad(dto.getCapacidad());
        }
        // Reasigna el apartamento dueño (o lo desvincula si viene null explícito).
        entity.setApartamento(resolveApartamento(dto.getApartamentoId()));

        return mapToDTO(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Estacionamiento no encontrado");
        }
        repository.deleteById(id);
    }

    private EstacionamientoResponseDTO mapToDTO(Estacionamiento entity) {
        return EstacionamientoResponseDTO.builder()
                .id(entity.getId())
                .codigo(entity.getCodigo())
                .estadoOcupacion(entity.getEstadoOcupacion())
                .tipoVehiculo(entity.getTipoVehiculo())
                .capacidad(entity.getCapacidad())
                .ocupacionActual(entity.getOcupacionActual())
                .zonaEstacionamientoId(entity.getZona().getId())
                .zonaNombre(entity.getZona().getNombre())
                .apartamentoId(entity.getApartamento() != null ? entity.getApartamento().getId() : null)
                .apartamentoNumero(entity.getApartamento() != null ? entity.getApartamento().getNumero() : null)
                .condominioId(entity.getZona().getCondominio().getId())
                .condominioNombre(entity.getZona().getCondominio().getNombre())
                .vehiculoActualId(entity.getVehiculoActual() != null ? entity.getVehiculoActual().getId() : null)
                .placaActual(entity.getVehiculoActual() != null ? entity.getVehiculoActual().getPlaca() : null)
                .build();
    }
}