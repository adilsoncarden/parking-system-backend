package com.condosaas.api.module.carrito_carga.service.impl;

import com.condosaas.api.module.carrito_carga.dto.*;
import com.condosaas.api.module.carrito_carga.model.*;
import com.condosaas.api.module.carrito_carga.repository.CarritoCargaRepository;
import com.condosaas.api.module.carrito_carga.service.CarritoCargaService;
import com.condosaas.api.module.condominio.model.Condominio;
import com.condosaas.api.module.condominio.repository.CondominioRepository;
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

    @Override
    public CarritoCargaResponseDTO create(CarritoCargaRequestDTO dto) {

        Condominio condominio = condominioRepository.findById(dto.getCondominioId())
                .orElseThrow(() -> new EntityNotFoundException("Condominio no encontrado"));

        CarritoCarga entity = CarritoCarga.builder()
                .codigo(dto.getCodigo())
                .descripcion(dto.getDescripcion())
                .estado(dto.getEstado())
                .condominio(condominio)
                .build();

        return mapToDTO(repository.save(entity));
    }

    @Override
    public CarritoCargaResponseDTO getById(Long id) {
        CarritoCarga entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Carrito no encontrado"));

        return mapToDTO(entity);
    }

    @Override
    public List<CarritoCargaResponseDTO> getAll(Long condominioId) {

        List<CarritoCarga> lista;

        if (condominioId != null) {
            lista = repository.findByCondominioId(condominioId);
        } else {
            lista = repository.findAll();
        }

        return lista.stream().map(this::mapToDTO).toList();
    }

    @Override
    public CarritoCargaResponseDTO update(Long id, CarritoCargaRequestDTO dto) {

        CarritoCarga entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Carrito no encontrado"));

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
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Carrito no encontrado");
        }
        repository.deleteById(id);
    }

    private CarritoCargaResponseDTO mapToDTO(CarritoCarga entity) {
        return CarritoCargaResponseDTO.builder()
                .id(entity.getId())
                .codigo(entity.getCodigo())
                .descripcion(entity.getDescripcion())
                .estado(entity.getEstado())
                .condominioId(entity.getCondominio().getId())
                .condominioNombre(entity.getCondominio().getNombre())
                .build();
    }
}