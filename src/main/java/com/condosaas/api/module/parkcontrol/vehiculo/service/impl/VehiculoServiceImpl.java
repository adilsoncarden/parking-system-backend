package com.condosaas.api.module.parkcontrol.vehiculo.service.impl;

import com.condosaas.api.exception.ResourceNotFoundException;
import com.condosaas.api.module.apartamento.model.Apartamento;
import com.condosaas.api.module.apartamento.repository.ApartamentoRepository;
import com.condosaas.api.module.condominio.model.Condominio;
import com.condosaas.api.module.condominio.repository.CondominioRepository;
import com.condosaas.api.module.parkcontrol.vehiculo.dto.VehiculoRequest;
import com.condosaas.api.module.parkcontrol.vehiculo.dto.VehiculoResponse;
import com.condosaas.api.module.parkcontrol.vehiculo.model.Vehiculo;
import com.condosaas.api.module.parkcontrol.vehiculo.repository.VehiculoRepository;
import com.condosaas.api.module.parkcontrol.vehiculo.service.VehiculoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class VehiculoServiceImpl implements VehiculoService {
    private final VehiculoRepository repository;
    private final CondominioRepository condominioRepository;
    private final ApartamentoRepository apartamentoRepository;

    public VehiculoServiceImpl(VehiculoRepository repository, CondominioRepository condominioRepository,
            ApartamentoRepository apartamentoRepository) {
        this.repository = repository;
        this.condominioRepository = condominioRepository;
        this.apartamentoRepository = apartamentoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculoResponse> findAll(Long condominioId) {
        var list = condominioId == null ? repository.findAll() : repository.findByCondominioId(condominioId);
        return list.stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public VehiculoResponse findById(Long id) {
        return toResponse(get(id));
    }

    @Override
    public VehiculoResponse create(VehiculoRequest request) {
        Vehiculo entity = new Vehiculo();
        apply(entity, request);
        return toResponse(repository.save(entity));
    }

    @Override
    public VehiculoResponse update(Long id, VehiculoRequest request) {
        Vehiculo entity = get(id);
        apply(entity, request);
        return toResponse(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        repository.delete(get(id));
    }

    private Vehiculo get(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehiculo no encontrado: " + id));
    }

    private void apply(Vehiculo entity, VehiculoRequest request) {
        entity.setPlaca(request.getPlaca());
        entity.setMarca(request.getMarca());
        entity.setModelo(request.getModelo());
        entity.setColor(request.getColor());
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

    private VehiculoResponse toResponse(Vehiculo entity) {
        return new VehiculoResponse(
                entity.getId(),
                entity.getPlaca(),
                entity.getMarca(),
                entity.getModelo(),
                entity.getColor(),
                entity.getCondominio() != null ? entity.getCondominio().getId() : null,
                entity.getApartamento() != null ? entity.getApartamento().getId() : null);
    }
}
