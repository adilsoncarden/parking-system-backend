package com.condosaas.api.module.pase_invitado.service.impl;

import com.condosaas.api.module.pase_invitado.dto.*;
import com.condosaas.api.module.pase_invitado.model.*;
import com.condosaas.api.module.pase_invitado.repository.PaseInvitadoRepository;
import com.condosaas.api.module.pase_invitado.service.PaseInvitadoService;
import com.condosaas.api.module.usuario.model.Usuario;
import com.condosaas.api.module.usuario.repository.UsuarioRepository;
import com.condosaas.api.module.vehiculo.model.Vehiculo;
import com.condosaas.api.module.vehiculo.repository.VehiculoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PaseInvitadoServiceImpl implements PaseInvitadoService {

    private final PaseInvitadoRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final VehiculoRepository vehiculoRepository;

    @Override
    public PaseInvitadoResponseDTO create(PaseInvitadoRequestDTO dto) {

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        Vehiculo vehiculo = null;
        if (dto.getVehiculoId() != null) {
            vehiculo = vehiculoRepository.findById(dto.getVehiculoId())
                    .orElseThrow(() -> new EntityNotFoundException("Vehículo no encontrado"));
        }

        PaseInvitado entity = PaseInvitado.builder()
                .codigo(dto.getCodigo())
                .nombreInvitado(dto.getNombreInvitado())
                .fechaInicio(dto.getFechaInicio())
                .fechaFin(dto.getFechaFin())
                .estado(dto.getEstado())
                .metodo(dto.getMetodo())
                .usuario(usuario)
                .vehiculo(vehiculo)
                .build();

        return mapToDTO(repository.save(entity));
    }

    @Override
    public PaseInvitadoResponseDTO getById(Long id) {
        PaseInvitado entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pase no encontrado"));

        return mapToDTO(entity);
    }

    @Override
    public List<PaseInvitadoResponseDTO> getAll(Long usuarioId) {

        List<PaseInvitado> lista;

        if (usuarioId != null) {
            lista = repository.findByUsuarioId(usuarioId);
        } else {
            lista = repository.findAll();
        }

        return lista.stream().map(this::mapToDTO).toList();
    }

    @Override
    public PaseInvitadoResponseDTO update(Long id, PaseInvitadoRequestDTO dto) {

        PaseInvitado entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pase no encontrado"));

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        Vehiculo vehiculo = null;
        if (dto.getVehiculoId() != null) {
            vehiculo = vehiculoRepository.findById(dto.getVehiculoId())
                    .orElseThrow(() -> new EntityNotFoundException("Vehículo no encontrado"));
        }

        entity.setCodigo(dto.getCodigo());
        entity.setNombreInvitado(dto.getNombreInvitado());
        entity.setFechaInicio(dto.getFechaInicio());
        entity.setFechaFin(dto.getFechaFin());
        entity.setEstado(dto.getEstado());
        entity.setMetodo(dto.getMetodo());
        entity.setUsuario(usuario);
        entity.setVehiculo(vehiculo);

        return mapToDTO(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Pase no encontrado");
        }
        repository.deleteById(id);
    }

    private PaseInvitadoResponseDTO mapToDTO(PaseInvitado entity) {
        return PaseInvitadoResponseDTO.builder()
                .id(entity.getId())
                .codigo(entity.getCodigo())
                .nombreInvitado(entity.getNombreInvitado())
                .fechaInicio(entity.getFechaInicio())
                .fechaFin(entity.getFechaFin())
                .estado(entity.getEstado())
                .metodo(entity.getMetodo())
                .usuarioId(entity.getUsuario().getId())
                .usuarioNombre(entity.getUsuario().getNombres() + " " + entity.getUsuario().getApellidos())
                .vehiculoId(entity.getVehiculo() != null ? entity.getVehiculo().getId() : null)
                .build();
    }
}