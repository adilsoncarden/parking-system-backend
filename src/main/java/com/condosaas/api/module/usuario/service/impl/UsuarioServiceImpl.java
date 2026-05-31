package com.condosaas.api.module.usuario.service.impl;

import com.condosaas.api.module.usuario.dto.*;
import com.condosaas.api.module.usuario.model.*;
import com.condosaas.api.module.usuario.repository.UsuarioRepository;
import com.condosaas.api.module.usuario.service.UsuarioService;
import com.condosaas.api.module.rol.model.Rol;
import com.condosaas.api.module.rol.repository.RolRepository;
import com.condosaas.api.module.apartamento.model.Apartamento;
import com.condosaas.api.module.apartamento.repository.ApartamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository repository;
    private final RolRepository rolRepository;
    private final ApartamentoRepository apartamentoRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UsuarioResponseDTO create(UsuarioRequestDTO dto) {

        Rol rol = rolRepository.findById(dto.getRolId())
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado"));

        Apartamento apartamento = null;
        if (dto.getApartamentoId() != null) {
            apartamento = apartamentoRepository.findById(dto.getApartamentoId())
                    .orElseThrow(() -> new EntityNotFoundException("Apartamento no encontrado"));
        }

        Usuario entity = Usuario.builder()
                .nombres(dto.getNombres())
                .apellidos(dto.getApellidos())
                .email(dto.getEmail())
                .telefono(dto.getTelefono())
                .password(encodePassword(dto.getPassword()))
                .tipoOcupante(dto.getTipoOcupante())
                .estado(dto.getEstado())
                .rol(rol)
                .apartamento(apartamento)
                .build();

        return mapToDTO(repository.save(entity));
    }

    @Override
    public UsuarioResponseDTO getById(Long id) {
        Usuario entity = repository.findByIdWithRol(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        return mapToDTO(entity);
    }

    @Override
    public List<UsuarioResponseDTO> getAll(Long rolId, Long apartamentoId) {

        List<Usuario> lista;

        if (rolId != null) {
            lista = repository.findByRolIdWithRol(rolId);
        } else if (apartamentoId != null) {
            lista = repository.findByApartamentoIdWithRol(apartamentoId);
        } else {
            lista = repository.findAllWithRol();
        }

        return lista.stream().map(this::mapToDTO).toList();
    }

    @Override
    public UsuarioResponseDTO update(Long id, UsuarioRequestDTO dto) {

        Usuario entity = repository.findByIdWithRol(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        Rol rol = rolRepository.findById(dto.getRolId())
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado"));

        Apartamento apartamento = null;
        if (dto.getApartamentoId() != null) {
            apartamento = apartamentoRepository.findById(dto.getApartamentoId())
                    .orElseThrow(() -> new EntityNotFoundException("Apartamento no encontrado"));
        }

        entity.setNombres(dto.getNombres());
        entity.setApellidos(dto.getApellidos());
        entity.setEmail(dto.getEmail());
        entity.setTelefono(dto.getTelefono());
        entity.setPassword(encodePassword(dto.getPassword()));
        entity.setTipoOcupante(dto.getTipoOcupante());
        entity.setEstado(dto.getEstado());
        entity.setRol(rol);
        entity.setApartamento(apartamento);

        return mapToDTO(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Usuario no encontrado");
        }
        repository.deleteById(id);
    }

    private String encodePassword(String raw) {
        if (raw == null || raw.isBlank()) {
            return raw;
        }
        if (raw.startsWith("$2a$") || raw.startsWith("$2b$") || raw.startsWith("$2y$")) {
            return raw;
        }
        return passwordEncoder.encode(raw);
    }

    private UsuarioResponseDTO mapToDTO(Usuario entity) {
        return UsuarioResponseDTO.builder()
                .id(entity.getId())
                .nombres(entity.getNombres())
                .apellidos(entity.getApellidos())
                .email(entity.getEmail())
                .telefono(entity.getTelefono())
                .tipoOcupante(entity.getTipoOcupante())
                .estado(entity.getEstado())
                .rolId(entity.getRol().getId())
                .rolNombre(entity.getRol().getNombre())
                .apartamentoId(entity.getApartamento() != null ? entity.getApartamento().getId() : null)
                .build();
    }
}