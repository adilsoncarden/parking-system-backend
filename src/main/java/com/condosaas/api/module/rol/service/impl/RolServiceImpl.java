package com.condosaas.api.module.rol.service.impl;

import com.condosaas.api.exception.ResourceNotFoundException;
import com.condosaas.api.module.rol.dto.RolRequest;
import com.condosaas.api.module.rol.dto.RolResponse;
import com.condosaas.api.module.rol.model.Rol;
import com.condosaas.api.module.rol.repository.RolRepository;
import com.condosaas.api.module.rol.service.RolService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class RolServiceImpl implements RolService {
    private final RolRepository repository;

    public RolServiceImpl(RolRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RolResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public RolResponse findById(Integer id) {
        return toResponse(get(id));
    }

    @Override
    public RolResponse create(RolRequest request) {
        Rol entity = new Rol();
        entity.setName(request.getName());
        return toResponse(repository.save(entity));
    }

    @Override
    public RolResponse update(Integer id, RolRequest request) {
        Rol entity = get(id);
        entity.setName(request.getName());
        return toResponse(repository.save(entity));
    }

    @Override
    public void delete(Integer id) {
        repository.delete(get(id));
    }

    private Rol get(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado: " + id));
    }

    private RolResponse toResponse(Rol entity) {
        return new RolResponse(entity.getId(), entity.getName());
    }
}
