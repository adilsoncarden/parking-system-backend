package com.condosaas.api.module.torre.service.impl;

import com.condosaas.api.module.condominio.model.Condominio;
import com.condosaas.api.module.condominio.repository.CondominioRepository;
import com.condosaas.api.module.torre.dto.*;
import com.condosaas.api.module.torre.model.*;
import com.condosaas.api.module.torre.repository.TorreRepository;
import com.condosaas.api.module.torre.service.TorreService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TorreServiceImpl implements TorreService {

    private final TorreRepository repository;
    private final CondominioRepository condominioRepository;

    @Override
    public TorreResponseDTO create(TorreRequestDTO dto) {

        Condominio condominio = condominioRepository.findById(dto.getCondominioId())
                .orElseThrow(() -> new EntityNotFoundException("Condominio no encontrado"));

        Torre entity = Torre.builder()
                .nombre(dto.getNombre())
                .estado(dto.getEstado())
                .condominio(condominio)
                .build();

        return mapToDTO(repository.save(entity));
    }

    @Override
    public TorreResponseDTO getById(Long id) {
        Torre entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Torre no encontrada"));

        return mapToDTO(entity);
    }

    @Override
    public List<TorreResponseDTO> getAll(Long condominioId) {

        List<Torre> lista;

        if (condominioId != null) {
            lista = repository.findByCondominioId(condominioId);
        } else {
            lista = repository.findAll();
        }

        return lista.stream().map(this::mapToDTO).toList();
    }

    @Override
    public TorreResponseDTO update(Long id, TorreRequestDTO dto) {

        Torre entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Torre no encontrada"));

        Condominio condominio = condominioRepository.findById(dto.getCondominioId())
                .orElseThrow(() -> new EntityNotFoundException("Condominio no encontrado"));

        entity.setNombre(dto.getNombre());
        entity.setEstado(dto.getEstado());
        entity.setCondominio(condominio);

        return mapToDTO(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Torre no encontrada");
        }
        repository.deleteById(id);
    }

    private TorreResponseDTO mapToDTO(Torre entity) {
        return TorreResponseDTO.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .estado(entity.getEstado())
                .condominioId(entity.getCondominio().getId())
                .condominioNombre(entity.getCondominio().getNombre())
                .build();
    }
}