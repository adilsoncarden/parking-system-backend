package com.condosaas.api.module.carrito_carga.service.impl;

import com.condosaas.api.module.carrito_carga.dto.*;
import com.condosaas.api.module.carrito_carga.model.*;
import com.condosaas.api.module.carrito_carga.repository.CarritoCargaRepository;
import com.condosaas.api.module.carrito_carga.service.CarritoCargaService;
import com.condosaas.api.module.condominio.model.Condominio;
import com.condosaas.api.module.condominio.repository.CondominioRepository;
import com.condosaas.api.module.entrada.model.Entrada;
import com.condosaas.api.module.entrada.repository.EntradaRepository;
import com.condosaas.api.exception.BusinessRuleException;
import com.condosaas.api.security.CurrentUser;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CarritoCargaServiceImpl implements CarritoCargaService {

    private final CarritoCargaRepository repository;
    private final CondominioRepository condominioRepository;
    private final EntradaRepository entradaRepository;
    private final CurrentUser currentUser;

    @Override
    public CarritoCargaResponseDTO create(CarritoCargaRequestDTO dto) {

        currentUser.assertCondominio(dto.getCondominioId());
        Condominio condominio = condominioRepository.findById(dto.getCondominioId())
                .orElseThrow(() -> new EntityNotFoundException("Condominio no encontrado"));

        // El carrito queda fijo a una entrada (puerta). Se valida la capacidad de esa puerta.
        if (dto.getEntradaId() == null) {
            throw new BusinessRuleException("Debes asignar una entrada (puerta) al carrito");
        }
        Entrada entrada = entradaRepository.findById(dto.getEntradaId())
                .orElseThrow(() -> new EntityNotFoundException("Entrada no encontrada"));
        if (!entrada.getCondominio().getId().equals(condominio.getId())) {
            throw new BusinessRuleException("La entrada no pertenece al condominio del carrito");
        }
        if (entrada.getCapacidadCarritos() != null
                && repository.countByEntradaId(entrada.getId()) >= entrada.getCapacidadCarritos()) {
            throw new BusinessRuleException(
                    "La entrada \"" + entrada.getNombre() + "\" ya alcanzó su capacidad de "
                            + entrada.getCapacidadCarritos() + " carritos");
        }

        CarritoCarga entity = CarritoCarga.builder()
                .codigo(dto.getCodigo())
                .descripcion(dto.getDescripcion())
                .estado(dto.getEstado())
                .condominio(condominio)
                .entrada(entrada)
                .build();

        return mapToDTO(repository.save(entity));
    }

    @Override
    public CarritoCargaResponseDTO getById(Long id) {
        CarritoCarga entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Carrito no encontrado"));
        currentUser.assertCondominio(entity.getCondominio().getId());

        return mapToDTO(entity);
    }

    @Override
    public List<CarritoCargaResponseDTO> getAll(Long condominioId) {

        Long scoped = currentUser.resolveFilter(condominioId);

        List<CarritoCarga> lista = (scoped != null)
                ? repository.findByCondominioId(scoped)
                : repository.findAll();

        return lista.stream().map(this::mapToDTO).toList();
    }

    @Override
    public CarritoCargaResponseDTO update(Long id, CarritoCargaRequestDTO dto) {

        CarritoCarga entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Carrito no encontrado"));
        currentUser.assertCondominio(entity.getCondominio().getId());
        currentUser.assertCondominio(dto.getCondominioId());

        Condominio condominio = condominioRepository.findById(dto.getCondominioId())
                .orElseThrow(() -> new EntityNotFoundException("Condominio no encontrado"));

        entity.setCodigo(dto.getCodigo());
        entity.setDescripcion(dto.getDescripcion());
        entity.setEstado(dto.getEstado());
        entity.setCondominio(condominio);

        return mapToDTO(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        CarritoCarga entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Carrito no encontrado"));
        currentUser.assertCondominio(entity.getCondominio().getId());
        repository.delete(entity);
    }

    private CarritoCargaResponseDTO mapToDTO(CarritoCarga entity) {
        return CarritoCargaResponseDTO.builder()
                .id(entity.getId())
                .codigo(entity.getCodigo())
                .descripcion(entity.getDescripcion())
                .estado(entity.getEstado())
                .condominioId(entity.getCondominio().getId())
                .condominioNombre(entity.getCondominio().getNombre())
                .entradaId(entity.getEntrada() != null ? entity.getEntrada().getId() : null)
                .entradaNombre(entity.getEntrada() != null ? entity.getEntrada().getNombre() : null)
                .build();
    }
}
