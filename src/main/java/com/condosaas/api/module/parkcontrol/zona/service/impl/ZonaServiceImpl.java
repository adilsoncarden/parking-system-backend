package com.condosaas.api.module.parkcontrol.zona.service.impl;

import com.condosaas.api.exception.ResourceNotFoundException;
import com.condosaas.api.module.condominio.model.Condominio;
import com.condosaas.api.module.condominio.repository.CondominioRepository;
import com.condosaas.api.module.parkcontrol.zona.dto.ZonaRequest;
import com.condosaas.api.module.parkcontrol.zona.dto.ZonaResponse;
import com.condosaas.api.module.parkcontrol.zona.model.Zona;
import com.condosaas.api.module.parkcontrol.zona.repository.ZonaRepository;
import com.condosaas.api.module.parkcontrol.zona.service.ZonaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class ZonaServiceImpl implements ZonaService {
    private final ZonaRepository repository;
    private final CondominioRepository condominioRepository;

    public ZonaServiceImpl(ZonaRepository repository, CondominioRepository condominioRepository) {
        this.repository = repository;
        this.condominioRepository = condominioRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ZonaResponse> findAll(Long condominioId) {
        var list = condominioId == null ? repository.findAll() : repository.findByCondominioId(condominioId);
        return list.stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ZonaResponse findById(Long id) {
        return toResponse(get(id));
    }

    @Override
    public ZonaResponse create(ZonaRequest request) {
        Zona entity = new Zona();
        apply(entity, request);
        return toResponse(repository.save(entity));
    }

    @Override
    public ZonaResponse update(Long id, ZonaRequest request) {
        Zona entity = get(id);
        apply(entity, request);
        return toResponse(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        repository.delete(get(id));
    }

    private Zona get(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zona no encontrada: " + id));
    }

    private void apply(Zona entity, ZonaRequest request) {
        entity.setNombre(request.getNombre());
        entity.setDescripcion(request.getDescripcion());
        entity.setCondominio(getCondominio(request.getIdCondominio()));
    }

    private Condominio getCondominio(Long id) {
        return condominioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Condominio no encontrado: " + id));
    }

    private ZonaResponse toResponse(Zona entity) {
        return new ZonaResponse(
                entity.getId(),
                entity.getNombre(),
                entity.getDescripcion(),
                entity.getCondominio() != null ? entity.getCondominio().getId() : null);
    }
}
