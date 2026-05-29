package com.condosaas.api.module.torre.service.impl;

import com.condosaas.api.exception.ResourceNotFoundException;
import com.condosaas.api.module.condominio.model.Condominio;
import com.condosaas.api.module.condominio.repository.CondominioRepository;
import com.condosaas.api.module.torre.dto.TorreRequest;
import com.condosaas.api.module.torre.dto.TorreResponse;
import com.condosaas.api.module.torre.model.Torre;
import com.condosaas.api.module.torre.repository.TorreRepository;
import com.condosaas.api.module.torre.service.TorreService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class TorreServiceImpl implements TorreService {
    private final TorreRepository repository;
    private final CondominioRepository condominioRepository;

    public TorreServiceImpl(TorreRepository repository, CondominioRepository condominioRepository) {
        this.repository = repository;
        this.condominioRepository = condominioRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TorreResponse> findAll(Long condominioId) {
        var list = condominioId == null ? repository.findAll() : repository.findByCondominioId(condominioId);
        return list.stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TorreResponse findById(Long id) {
        return toResponse(get(id));
    }

    @Override
    public TorreResponse create(TorreRequest request) {
        Torre entity = new Torre();
        apply(entity, request);
        return toResponse(repository.save(entity));
    }

    @Override
    public TorreResponse update(Long id, TorreRequest request) {
        Torre entity = get(id);
        apply(entity, request);
        return toResponse(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        repository.delete(get(id));
    }

    private Torre get(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Torre no encontrada: " + id));
    }

    private void apply(Torre entity, TorreRequest request) {
        entity.setNombre(request.getNombre());
        entity.setCantidadPisos(request.getCantidadPisos());
        entity.setCantidadApartamentos(request.getCantidadApartamentos());
        entity.setCondominio(getCondominio(request.getIdCondominio()));
    }

    private Condominio getCondominio(Long id) {
        return condominioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Condominio no encontrado: " + id));
    }

    private TorreResponse toResponse(Torre entity) {
        return new TorreResponse(
                entity.getIdTorres(),
                entity.getNombre(),
                entity.getCantidadPisos(),
                entity.getCantidadApartamentos(),
                entity.getCreatedAt(),
                entity.getCondominio() != null ? entity.getCondominio().getId() : null);
    }
}
