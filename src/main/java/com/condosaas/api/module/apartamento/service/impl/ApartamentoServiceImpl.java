package com.condosaas.api.module.apartamento.service.impl;

import com.condosaas.api.exception.ResourceNotFoundException;
import com.condosaas.api.module.piso.model.Piso;
import com.condosaas.api.module.piso.repository.PisoRepository;
import com.condosaas.api.module.apartamento.dto.ApartamentoRequest;
import com.condosaas.api.module.apartamento.dto.ApartamentoResponse;
import com.condosaas.api.module.apartamento.model.Apartamento;
import com.condosaas.api.module.apartamento.repository.ApartamentoRepository;
import com.condosaas.api.module.apartamento.service.ApartamentoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class ApartamentoServiceImpl implements ApartamentoService {
    private final ApartamentoRepository repository;
    private final PisoRepository pisoRepository;

    public ApartamentoServiceImpl(ApartamentoRepository repository, PisoRepository pisoRepository) {
        this.repository = repository;
        this.pisoRepository = pisoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApartamentoResponse> findAll(Long pisoId) {
        var list = pisoId == null ? repository.findAll() : repository.findByPisoId(pisoId);
        return list.stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ApartamentoResponse findById(Long id) {
        return toResponse(get(id));
    }

    @Override
    public ApartamentoResponse create(ApartamentoRequest request) {
        Apartamento entity = new Apartamento();
        apply(entity, request);
        return toResponse(repository.save(entity));
    }

    @Override
    public ApartamentoResponse update(Long id, ApartamentoRequest request) {
        Apartamento entity = get(id);
        apply(entity, request);
        return toResponse(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        repository.delete(get(id));
    }

    private Apartamento get(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Apartamento no encontrado: " + id));
    }

    private void apply(Apartamento entity, ApartamentoRequest request) {
        entity.setNumeroApartamento(request.getNumeroApartamento());
        entity.setPropietario(request.getPropietario());
        entity.setEstado(request.getEstado());
        entity.setPiso(getPiso(request.getIdPiso()));
    }

    private Piso getPiso(Long id) {
        return pisoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Piso no encontrado: " + id));
    }

    private ApartamentoResponse toResponse(Apartamento entity) {
        return new ApartamentoResponse(
                entity.getId(),
                entity.getNumeroApartamento(),
                entity.getPropietario(),
                entity.getEstado(),
                entity.getPiso() != null ? entity.getPiso().getId() : null,
                entity.getCreatedAt());
    }
}
