package com.condosaas.api.module.parkcontrol.pase_invitado.service.impl;

import com.condosaas.api.exception.ResourceNotFoundException;
import com.condosaas.api.module.apartamento.model.Apartamento;
import com.condosaas.api.module.apartamento.repository.ApartamentoRepository;
import com.condosaas.api.module.condominio.model.Condominio;
import com.condosaas.api.module.condominio.repository.CondominioRepository;
import com.condosaas.api.module.parkcontrol.pase_invitado.dto.PaseInvitadoRequest;
import com.condosaas.api.module.parkcontrol.pase_invitado.dto.PaseInvitadoResponse;
import com.condosaas.api.module.parkcontrol.pase_invitado.model.PaseInvitado;
import com.condosaas.api.module.parkcontrol.pase_invitado.repository.PaseInvitadoRepository;
import com.condosaas.api.module.parkcontrol.pase_invitado.service.PaseInvitadoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class PaseInvitadoServiceImpl implements PaseInvitadoService {
    private final PaseInvitadoRepository repository;
    private final CondominioRepository condominioRepository;
    private final ApartamentoRepository apartamentoRepository;

    public PaseInvitadoServiceImpl(PaseInvitadoRepository repository, CondominioRepository condominioRepository,
            ApartamentoRepository apartamentoRepository) {
        this.repository = repository;
        this.condominioRepository = condominioRepository;
        this.apartamentoRepository = apartamentoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaseInvitadoResponse> findAll(Long condominioId, Boolean activos) {
        List<PaseInvitado> list;
        if (Boolean.TRUE.equals(activos)) {
            list = repository.findByActivoTrue();
        } else if (condominioId != null) {
            list = repository.findByCondominioId(condominioId);
        } else {
            list = repository.findAll();
        }
        return list.stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PaseInvitadoResponse findById(Long id) {
        return toResponse(get(id));
    }

    @Override
    public PaseInvitadoResponse create(PaseInvitadoRequest request) {
        PaseInvitado entity = new PaseInvitado();
        apply(entity, request);
        entity.setActivo(false);
        return toResponse(repository.save(entity));
    }

    @Override
    public PaseInvitadoResponse update(Long id, PaseInvitadoRequest request) {
        PaseInvitado entity = get(id);
        apply(entity, request);
        return toResponse(repository.save(entity));
    }

    @Override
    public PaseInvitadoResponse activar(Long id) {
        PaseInvitado entity = get(id);
        entity.setActivo(true);
        return toResponse(repository.save(entity));
    }

    @Override
    public PaseInvitadoResponse desactivar(Long id) {
        PaseInvitado entity = get(id);
        entity.setActivo(false);
        return toResponse(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        repository.delete(get(id));
    }

    private PaseInvitado get(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PaseInvitado no encontrado: " + id));
    }

    private void apply(PaseInvitado entity, PaseInvitadoRequest request) {
        entity.setCodigo(request.getCodigo());
        entity.setNombreVisitante(request.getNombreVisitante());
        entity.setPlacaVisitante(request.getPlacaVisitante());
        entity.setFechaInicio(request.getFechaInicio());
        entity.setFechaFin(request.getFechaFin());
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

    private PaseInvitadoResponse toResponse(PaseInvitado entity) {
        return new PaseInvitadoResponse(
                entity.getId(),
                entity.getActivo(),
                entity.getCodigo(),
                entity.getNombreVisitante(),
                entity.getPlacaVisitante(),
                entity.getFechaInicio(),
                entity.getFechaFin(),
                entity.getCondominio() != null ? entity.getCondominio().getId() : null,
                entity.getApartamento() != null ? entity.getApartamento().getId() : null);
    }
}
