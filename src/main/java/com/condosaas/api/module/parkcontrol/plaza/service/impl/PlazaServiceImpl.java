package com.condosaas.api.module.parkcontrol.plaza.service.impl;

import com.condosaas.api.exception.ResourceNotFoundException;
import com.condosaas.api.module.enums.EstadoOcupacion;
import com.condosaas.api.module.parkcontrol.zona.model.Zona;
import com.condosaas.api.module.parkcontrol.zona.repository.ZonaRepository;
import com.condosaas.api.module.parkcontrol.plaza.dto.PlazaRequest;
import com.condosaas.api.module.parkcontrol.plaza.dto.PlazaResponse;
import com.condosaas.api.module.parkcontrol.plaza.model.Plaza;
import com.condosaas.api.module.parkcontrol.plaza.repository.PlazaRepository;
import com.condosaas.api.module.parkcontrol.plaza.service.PlazaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class PlazaServiceImpl implements PlazaService {
    private final PlazaRepository repository;
    private final ZonaRepository zonaRepository;

    public PlazaServiceImpl(PlazaRepository repository, ZonaRepository zonaRepository) {
        this.repository = repository;
        this.zonaRepository = zonaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlazaResponse> findAll(Long idZona, Long condominioId) {
        List<Plaza> list;
        if (idZona != null) {
            list = repository.findByZonaId(idZona);
        } else if (condominioId != null) {
            list = repository.findByZonaCondominioId(condominioId);
        } else {
            list = repository.findAll();
        }
        return list.stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PlazaResponse findById(Long id) {
        return toResponse(get(id));
    }

    @Override
    public PlazaResponse create(PlazaRequest request) {
        Plaza entity = new Plaza();
        apply(entity, request);
        return toResponse(repository.save(entity));
    }

    @Override
    public PlazaResponse update(Long id, PlazaRequest request) {
        Plaza entity = get(id);
        apply(entity, request);
        return toResponse(repository.save(entity));
    }

    @Override
    public PlazaResponse cambiarEstado(Long id, EstadoOcupacion estado) {
        Plaza entity = get(id);
        entity.setEstado(estado);
        return toResponse(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        repository.delete(get(id));
    }

    private Plaza get(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plaza no encontrada: " + id));
    }

    private void apply(Plaza entity, PlazaRequest request) {
        entity.setCodigo(request.getCodigo());
        entity.setEstado(request.getEstado());
        entity.setZona(getZona(request.getIdZona()));
    }

    private Zona getZona(Long id) {
        return zonaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zona no encontrada: " + id));
    }

    private PlazaResponse toResponse(Plaza entity) {
        return new PlazaResponse(
                entity.getId(),
                entity.getCodigo(),
                entity.getEstado(),
                entity.getZona() != null ? entity.getZona().getId() : null);
    }
}
