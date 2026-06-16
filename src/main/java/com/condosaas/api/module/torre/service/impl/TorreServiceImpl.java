package com.condosaas.api.module.torre.service.impl;

import com.condosaas.api.module.condominio.model.Condominio;
import com.condosaas.api.module.condominio.repository.CondominioRepository;
import com.condosaas.api.module.torre.dto.*;
import com.condosaas.api.module.torre.model.*;
import com.condosaas.api.module.torre.repository.TorreRepository;
import com.condosaas.api.module.torre.service.TorreService;
import com.condosaas.api.security.CurrentUser;
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
    private final CurrentUser currentUser;

    @Override
    public TorreResponseDTO create(TorreRequestDTO dto) {

        currentUser.assertCondominio(dto.getCondominioId());
        Condominio condominio = condominioRepository.findById(dto.getCondominioId())
                .orElseThrow(() -> new EntityNotFoundException("Condominio no encontrado"));

        Torre entity = Torre.builder()
                .nombre(dto.getNombre())
                .condominio(condominio)
                .build();

        return mapToDTO(repository.save(entity));
    }

    @Override
    public TorreResponseDTO getById(Long id) {
        Torre entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Torre no encontrada"));
        currentUser.assertCondominio(entity.getCondominio().getId());

        return mapToDTO(entity);
    }

    @Override
    public List<TorreResponseDTO> getAll(Long condominioId) {

        Long scoped = currentUser.resolveFilter(condominioId);

        List<Torre> lista = (scoped != null)
                ? repository.findByCondominioId(scoped)
                : repository.findAll();

        return lista.stream().map(this::mapToDTO).toList();
    }

    @Override
    public TorreResponseDTO update(Long id, TorreRequestDTO dto) {

        Torre entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Torre no encontrada"));
        currentUser.assertCondominio(entity.getCondominio().getId());
        currentUser.assertCondominio(dto.getCondominioId());

        Condominio condominio = condominioRepository.findById(dto.getCondominioId())
                .orElseThrow(() -> new EntityNotFoundException("Condominio no encontrado"));

        entity.setNombre(dto.getNombre());
        entity.setCondominio(condominio);

        return mapToDTO(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        Torre entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Torre no encontrada"));
        currentUser.assertCondominio(entity.getCondominio().getId());
        repository.delete(entity);
    }

    private TorreResponseDTO mapToDTO(Torre entity) {
        return TorreResponseDTO.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .condominioId(entity.getCondominio().getId())
                .condominioNombre(entity.getCondominio().getNombre())
                .build();
    }
}
