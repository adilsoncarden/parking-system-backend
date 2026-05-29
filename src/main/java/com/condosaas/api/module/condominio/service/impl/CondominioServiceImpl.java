package com.condosaas.api.module.condominio.service.impl;

import com.condosaas.api.exception.ResourceNotFoundException;
import com.condosaas.api.module.condominio.dto.CondominioRequest;
import com.condosaas.api.module.condominio.dto.CondominioResponse;
import com.condosaas.api.module.condominio.model.Condominio;
import com.condosaas.api.module.condominio.repository.CondominioRepository;
import com.condosaas.api.module.condominio.service.CondominioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class CondominioServiceImpl implements CondominioService {
    private final CondominioRepository repository;

    public CondominioServiceImpl(CondominioRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CondominioResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CondominioResponse findById(Long id) {
        return toResponse(get(id));
    }

    @Override
    public CondominioResponse create(CondominioRequest request) {
        Condominio entity = new Condominio();
        apply(entity, request);
        return toResponse(repository.save(entity));
    }

    @Override
    public CondominioResponse update(Long id, CondominioRequest request) {
        Condominio entity = get(id);
        apply(entity, request);
        return toResponse(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        repository.delete(get(id));
    }

    private Condominio get(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Condominio no encontrado: " + id));
    }

    private void apply(Condominio entity, CondominioRequest request) {
        entity.setNombre(request.getNombre());
        entity.setDireccion(request.getDireccion());
        entity.setTipo(request.getTipo());
        entity.setImagen(request.getImagen());
        entity.setLatitud(request.getLatitud());
        entity.setLongitud(request.getLongitud());
    }

    private CondominioResponse toResponse(Condominio entity) {
        return new CondominioResponse(
                entity.getId(),
                entity.getNombre(),
                entity.getDireccion(),
                entity.getTipo(),
                entity.getImagen(),
                entity.getLatitud(),
                entity.getLongitud(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }
}
