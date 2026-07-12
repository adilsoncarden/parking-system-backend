package com.condosaas.api.module.inquilino_temporal.service.impl;

import com.condosaas.api.module.estacionamiento.model.Estacionamiento;
import com.condosaas.api.module.estacionamiento.repository.EstacionamientoRepository;
import com.condosaas.api.module.inquilino_temporal.dto.*;
import com.condosaas.api.module.inquilino_temporal.model.EstadoInquilino;
import com.condosaas.api.module.inquilino_temporal.model.InquilinoTemporal;
import com.condosaas.api.module.inquilino_temporal.repository.InquilinoTemporalRepository;
import com.condosaas.api.module.inquilino_temporal.service.InquilinoTemporalService;
import com.condosaas.api.module.usuario.model.Usuario;
import com.condosaas.api.module.usuario.repository.UsuarioRepository;
import com.condosaas.api.security.CurrentUser;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InquilinoTemporalServiceImpl implements InquilinoTemporalService {

    private final InquilinoTemporalRepository repository;
    private final EstacionamientoRepository estacionamientoRepository;
    private final UsuarioRepository usuarioRepository;
    private final CurrentUser currentUser;

    private Long condominioDe(Estacionamiento plaza) {
        return plaza.getZona().getCondominio().getId();
    }

    @Override
    public InquilinoTemporalResponseDTO create(InquilinoTemporalRequestDTO dto) {
        Estacionamiento plaza = estacionamientoRepository.findById(dto.getEstacionamientoId())
                .orElseThrow(() -> new EntityNotFoundException("Estacionamiento no encontrado"));
        currentUser.assertCondominio(condominioDe(plaza));

        Usuario propietario = usuarioRepository.findById(dto.getPropietarioId())
                .orElseThrow(() -> new EntityNotFoundException("Propietario no encontrado"));

        InquilinoTemporal entity = InquilinoTemporal.builder()
                .nombreCompleto(dto.getNombreCompleto())
                .documento(dto.getDocumento())
                .telefono(dto.getTelefono())
                .fechaInicio(dto.getFechaInicio())
                .fechaFin(dto.getFechaFin())
                .estado(dto.getEstado() != null ? dto.getEstado() : EstadoInquilino.ACTIVO)
                .estacionamiento(plaza)
                .propietario(propietario)
                .build();

        return mapToDTO(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public InquilinoTemporalResponseDTO getById(Long id) {
        InquilinoTemporal entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inquilino temporal no encontrado"));
        currentUser.assertCondominio(condominioDe(entity.getEstacionamiento()));
        return mapToDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InquilinoTemporalResponseDTO> getAll(Long estacionamientoId) {
        List<InquilinoTemporal> lista;
        if (currentUser.isScoped()) {
            lista = repository.findByEstacionamiento_Zona_Condominio_Id(currentUser.condominioId());
        } else if (estacionamientoId != null) {
            lista = repository.findByEstacionamientoId(estacionamientoId);
        } else {
            lista = repository.findAll();
        }
        return lista.stream().map(this::mapToDTO).toList();
    }

    @Override
    public InquilinoTemporalResponseDTO update(Long id, InquilinoTemporalRequestDTO dto) {
        InquilinoTemporal entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inquilino temporal no encontrado"));
        currentUser.assertCondominio(condominioDe(entity.getEstacionamiento()));

        Estacionamiento plaza = estacionamientoRepository.findById(dto.getEstacionamientoId())
                .orElseThrow(() -> new EntityNotFoundException("Estacionamiento no encontrado"));
        currentUser.assertCondominio(condominioDe(plaza));

        Usuario propietario = usuarioRepository.findById(dto.getPropietarioId())
                .orElseThrow(() -> new EntityNotFoundException("Propietario no encontrado"));

        entity.setNombreCompleto(dto.getNombreCompleto());
        entity.setDocumento(dto.getDocumento());
        entity.setTelefono(dto.getTelefono());
        entity.setFechaInicio(dto.getFechaInicio());
        entity.setFechaFin(dto.getFechaFin());
        if (dto.getEstado() != null) {
            entity.setEstado(dto.getEstado());
        }
        entity.setEstacionamiento(plaza);
        entity.setPropietario(propietario);

        return mapToDTO(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        InquilinoTemporal entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inquilino temporal no encontrado"));
        currentUser.assertCondominio(condominioDe(entity.getEstacionamiento()));
        repository.delete(entity);
    }

    private InquilinoTemporalResponseDTO mapToDTO(InquilinoTemporal e) {
        Estacionamiento plaza = e.getEstacionamiento();
        return InquilinoTemporalResponseDTO.builder()
                .id(e.getId())
                .nombreCompleto(e.getNombreCompleto())
                .documento(e.getDocumento())
                .telefono(e.getTelefono())
                .fechaInicio(e.getFechaInicio())
                .fechaFin(e.getFechaFin())
                .estado(e.getEstado())
                .estacionamientoId(plaza.getId())
                .estacionamientoCodigo(plaza.getCodigo())
                .propietarioId(e.getPropietario().getId())
                .propietarioNombre(e.getPropietario().getNombres() + " " + e.getPropietario().getApellidos())
                .condominioId(plaza.getZona().getCondominio().getId())
                .build();
    }
}
