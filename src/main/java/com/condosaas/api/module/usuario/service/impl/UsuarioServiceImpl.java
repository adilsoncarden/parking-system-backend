package com.condosaas.api.module.usuario.service.impl;

import com.condosaas.api.module.usuario.dto.*;
import com.condosaas.api.module.usuario.model.*;
import com.condosaas.api.module.usuario.repository.UsuarioRepository;
import com.condosaas.api.module.usuario.service.UsuarioService;
import com.condosaas.api.module.rol.model.Rol;
import com.condosaas.api.module.rol.repository.RolRepository;
import com.condosaas.api.module.apartamento.model.Apartamento;
import com.condosaas.api.module.apartamento.repository.ApartamentoRepository;
import com.condosaas.api.module.condominio.model.Condominio;
import com.condosaas.api.module.condominio.repository.CondominioRepository;
import com.condosaas.api.module.entrada.model.Entrada;
import com.condosaas.api.module.entrada.repository.EntradaRepository;
import com.condosaas.api.security.CurrentUser;
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
    private final CondominioRepository condominioRepository;
    private final EntradaRepository entradaRepository;
    private final CurrentUser currentUser;
    private final PasswordEncoder passwordEncoder;

    private Condominio resolveCondominio(Long condominioId) {
        if (condominioId == null) {
            return null;
        }
        return condominioRepository.findById(condominioId)
                .orElseThrow(() -> new EntityNotFoundException("Condominio no encontrado"));
    }

    // Entrada que cubre un portero (opcional). Null para roles que no son portero.
    private Entrada resolveEntrada(Long entradaId) {
        if (entradaId == null) {
            return null;
        }
        return entradaRepository.findById(entradaId)
                .orElseThrow(() -> new EntityNotFoundException("Entrada no encontrada"));
    }

    // Condominio efectivo de un usuario: el asignado directo, o el de su apartamento.
    private Long condominioDeUsuario(Usuario u) {
        if (u.getCondominio() != null) {
            return u.getCondominio().getId();
        }
        if (u.getApartamento() != null) {
            return u.getApartamento().getPiso().getTorre().getCondominio().getId();
        }
        return null;
    }

    @Override
    public UsuarioResponseDTO create(UsuarioRequestDTO dto) {

        if (dto.getCondominioId() != null) {
            currentUser.assertCondominio(dto.getCondominioId());
        }

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
                .condominio(resolveCondominio(dto.getCondominioId()))
                .entrada(resolveEntrada(dto.getEntradaId()))
                .turno(dto.getTurno())
                .build();

        return mapToDTO(repository.save(entity));
    }

    @Override
    public UsuarioResponseDTO getById(Long id) {
        Usuario entity = repository.findByIdWithRol(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        currentUser.assertCondominio(condominioDeUsuario(entity));

        return mapToDTO(entity);
    }

    @Override
    public List<UsuarioResponseDTO> getAll(Long rolId, Long apartamentoId, Long condominioId) {

        if (currentUser.isScoped()) {
            return repository.findAllByCondominioScope(currentUser.condominioId())
                    .stream().map(this::mapToDTO).toList();
        }

        if (condominioId != null) {
            return repository.findAllByCondominioScope(condominioId)
                    .stream().map(this::mapToDTO).toList();
        }

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

        // Aislamiento por condominio: un admin "scoped" no puede editar usuarios de otro
        // condominio (ni el actual del usuario, ni moverlo hacia uno ajeno).
        currentUser.assertCondominio(condominioDeUsuario(entity));
        if (dto.getCondominioId() != null) {
            currentUser.assertCondominio(dto.getCondominioId());
        }

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
        // La contraseña solo se cambia si llega una nueva; si viene vacía se conserva la
        // actual (evita dejar al usuario sin contraseña al editar otros campos).
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            entity.setPassword(encodePassword(dto.getPassword()));
        }
        entity.setTipoOcupante(dto.getTipoOcupante());
        entity.setEstado(dto.getEstado());
        entity.setRol(rol);
        entity.setApartamento(apartamento);
        entity.setCondominio(resolveCondominio(dto.getCondominioId()));
        entity.setEntrada(resolveEntrada(dto.getEntradaId()));
        entity.setTurno(dto.getTurno());

        return mapToDTO(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        Usuario entity = repository.findByIdWithRol(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        // Un admin "scoped" solo puede eliminar usuarios de su propio condominio.
        currentUser.assertCondominio(condominioDeUsuario(entity));
        repository.delete(entity);
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
                .unidad(entity.getApartamento() != null ? entity.getApartamento().getNumero() : null)
                .condominioId(entity.getCondominio() != null ? entity.getCondominio().getId() : null)
                .condominioNombre(entity.getCondominio() != null ? entity.getCondominio().getNombre() : null)
                .entradaId(entity.getEntrada() != null ? entity.getEntrada().getId() : null)
                .entradaNombre(entity.getEntrada() != null ? entity.getEntrada().getNombre() : null)
                .turno(entity.getTurno())
                .build();
    }
}