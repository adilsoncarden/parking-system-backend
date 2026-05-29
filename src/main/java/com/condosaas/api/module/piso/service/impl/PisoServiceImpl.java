package com.condosaas.api.module.piso.service.impl;

import com.condosaas.api.exception.ResourceNotFoundException;
import com.condosaas.api.module.torre.model.Torre;
import com.condosaas.api.module.torre.repository.TorreRepository;
import com.condosaas.api.module.piso.dto.PisoRequest;
import com.condosaas.api.module.piso.dto.PisoResponse;
import com.condosaas.api.module.piso.model.Piso;
import com.condosaas.api.module.piso.repository.PisoRepository;
import com.condosaas.api.module.piso.service.PisoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class PisoServiceImpl implements PisoService {
    private final PisoRepository repository;
    private final TorreRepository torreRepository;

    public PisoServiceImpl(PisoRepository repository, TorreRepository torreRepository) {
        this.repository = repository;
        this.torreRepository = torreRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PisoResponse> findAll(Long torreId) {
        var list = torreId == null ? repository.findAll() : repository.findByTorreIdTorres(torreId);
        return list.stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PisoResponse findById(Long id) {
        return toResponse(get(id));
    }

    @Override
    public PisoResponse create(PisoRequest request) {
        Piso entity = new Piso();
        apply(entity, request);
        return toResponse(repository.save(entity));
    }

    @Override
    public PisoResponse update(Long id, PisoRequest request) {
        Piso entity = get(id);
        apply(entity, request);
        return toResponse(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        repository.delete(get(id));
    }

    private Piso get(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Piso no encontrado: " + id));
    }

    private void apply(Piso entity, PisoRequest request) {
        entity.setNumeroPiso(request.getNumeroPiso());
        entity.setTorre(getTorre(request.getIdTorre()));
    }

    private Torre getTorre(Long id) {
        return torreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Torre no encontrada: " + id));
    }

    private PisoResponse toResponse(Piso entity) {
        return new PisoResponse(
                entity.getId(),
                entity.getNumeroPiso(),
                entity.getTorre() != null ? entity.getTorre().getIdTorres() : null);
    }
}
