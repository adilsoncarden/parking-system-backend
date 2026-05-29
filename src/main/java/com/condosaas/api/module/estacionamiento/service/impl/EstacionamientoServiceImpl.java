package com.condosaas.api.module.estacionamiento.service.impl;

import com.condosaas.api.exception.ResourceNotFoundException;
import com.condosaas.api.module.apartamento.model.Apartamento;
import com.condosaas.api.module.apartamento.repository.ApartamentoRepository;
import com.condosaas.api.module.condominio.model.Condominio;
import com.condosaas.api.module.condominio.repository.CondominioRepository;
import com.condosaas.api.module.enums.EstadoOcupacion;
import com.condosaas.api.module.estacionamiento.dto.EstacionamientoRequest;
import com.condosaas.api.module.estacionamiento.dto.EstacionamientoResponse;
import com.condosaas.api.module.estacionamiento.model.Estacionamiento;
import com.condosaas.api.module.estacionamiento.repository.EstacionamientoRepository;
import com.condosaas.api.module.estacionamiento.service.EstacionamientoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class EstacionamientoServiceImpl implements EstacionamientoService {
    private final EstacionamientoRepository repository;
    private final CondominioRepository condominioRepository;
    private final ApartamentoRepository apartamentoRepository;

    public EstacionamientoServiceImpl(EstacionamientoRepository repository, CondominioRepository condominioRepository, ApartamentoRepository apartamentoRepository) {
        this.repository = repository;
        this.condominioRepository = condominioRepository;
        this.apartamentoRepository = apartamentoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<EstacionamientoResponse> findAll(Long condominioId) {
        var list = condominioId == null ? repository.findAll() : repository.findByCondominioId(condominioId);
        return list.stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public EstacionamientoResponse findById(Long id) {
        return toResponse(get(id));
    }

    @Override
    public EstacionamientoResponse create(EstacionamientoRequest request) {
        Estacionamiento entity = new Estacionamiento();
        apply(entity, request);
        return toResponse(repository.save(entity));
    }

    @Override
    public EstacionamientoResponse update(Long id, EstacionamientoRequest request) {
        Estacionamiento entity = get(id);
        apply(entity, request);
        return toResponse(repository.save(entity));
    }

    @Override
    public EstacionamientoResponse updateEstado(Long id, EstadoOcupacion estado) {
        Estacionamiento entity = get(id);
        entity.setEstado(estado);
        return toResponse(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        repository.delete(get(id));
    }

    private Estacionamiento get(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estacionamiento no encontrado: " + id));
    }

    private void apply(Estacionamiento entity, EstacionamientoRequest request) {
        entity.setCodigo(request.getCodigo());
        entity.setEstado(request.getEstado());
        entity.setTipo(request.getTipo());
        entity.setCondominio(getCondominio(request.getIdCondominio()));
        entity.setApartamento(request.getIdApartamento() == null ? null : getApartamento(request.getIdApartamento()));
    }

    private Condominio getCondominio(Long id) {
        return condominioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Condominio no encontrado: " + id));
    }

    private Apartamento getApartamento(Long id) {
        return apartamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Apartamento no encontrado: " + id));
    }

    private EstacionamientoResponse toResponse(Estacionamiento entity) {
        return new EstacionamientoResponse(
                entity.getId(),
                entity.getCodigo(),
                entity.getEstado(),
                entity.getTipo(),
                entity.getApartamento() != null ? entity.getApartamento().getId() : null,
                entity.getCondominio() != null ? entity.getCondominio().getId() : null
        );
    }
}
