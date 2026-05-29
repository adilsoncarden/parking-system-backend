package com.condosaas.api.module.usuario.service.impl;

import com.condosaas.api.exception.ResourceNotFoundException;
import com.condosaas.api.module.rol.model.Rol;
import com.condosaas.api.module.rol.repository.RolRepository;
import com.condosaas.api.module.usuario.dto.UsuarioRequest;
import com.condosaas.api.module.usuario.dto.UsuarioResponse;
import com.condosaas.api.module.usuario.model.Usuario;
import com.condosaas.api.module.usuario.repository.UsuarioRepository;
import com.condosaas.api.module.usuario.service.UsuarioService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository repository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository repository, RolRepository rolRepository,
            PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponse findById(Long id) {
        return toResponse(get(id));
    }

    @Override
    public UsuarioResponse create(UsuarioRequest request) {
        Usuario entity = new Usuario();
        entity.setUsername(request.getUsername());
        entity.setPassword(passwordEncoder.encode(request.getPassword()));
        entity.setRole(getRol(request.getRoleId()));
        return toResponse(repository.save(entity));
    }

    @Override
    public UsuarioResponse update(Long id, UsuarioRequest request) {
        Usuario entity = get(id);
        entity.setUsername(request.getUsername());
        entity.setPassword(passwordEncoder.encode(request.getPassword()));
        entity.setRole(getRol(request.getRoleId()));
        return toResponse(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        repository.delete(get(id));
    }

    private Usuario get(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + id));
    }

    private Rol getRol(Integer id) {
        return rolRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado: " + id));
    }

    private UsuarioResponse toResponse(Usuario entity) {
        return new UsuarioResponse(
                entity.getId(),
                entity.getUsername(),
                entity.getRole() != null ? entity.getRole().getId() : null,
                entity.getRole() != null ? entity.getRole().getName() : null);
    }
}
