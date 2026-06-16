package com.condosaas.api.module.piso.service.impl;

import com.condosaas.api.module.piso.dto.*;
import com.condosaas.api.module.piso.model.*;
import com.condosaas.api.module.piso.repository.PisoRepository;
import com.condosaas.api.module.piso.service.PisoService;
import com.condosaas.api.module.torre.model.Torre;
import com.condosaas.api.module.torre.repository.TorreRepository;
import com.condosaas.api.security.CurrentUser;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PisoServiceImpl implements PisoService {

    private final PisoRepository repository;
    private final TorreRepository torreRepository;
    private final CurrentUser currentUser;

    @Override
    public PisoResponseDTO create(PisoRequestDTO dto) {

        Torre torre = torreRepository.findById(dto.getTorreId())
                .orElseThrow(() -> new EntityNotFoundException("Torre no encontrada"));
        currentUser.assertCondominio(torre.getCondominio().getId());

        Piso entity = Piso.builder()
                .numero(dto.getNumero())
                .torre(torre)
                .build();

        return mapToDTO(repository.save(entity));
    }

    @Override
    public PisoResponseDTO getById(Long id) {
        Piso entity = repository.findByIdWithRelations(id)
                .orElseThrow(() -> new EntityNotFoundException("Piso no encontrado"));
        currentUser.assertCondominio(entity.getTorre().getCondominio().getId());

        return mapToDTO(entity);
    }

    @Override
    public List<PisoResponseDTO> getAll(Long torreId) {

        List<Piso> lista;
        if (currentUser.isScoped()) {
            lista = repository.findByCondominioIdWithRelations(currentUser.condominioId());
        } else if (torreId != null) {
            lista = repository.findByTorreIdWithRelations(torreId);
        } else {
            lista = repository.findAllWithRelations();
        }

        return lista.stream().map(this::mapToDTO).toList();
    }

    @Override
    public PisoResponseDTO update(Long id, PisoRequestDTO dto) {

        Piso entity = repository.findByIdWithRelations(id)
                .orElseThrow(() -> new EntityNotFoundException("Piso no encontrado"));
        currentUser.assertCondominio(entity.getTorre().getCondominio().getId());

        Torre torre = torreRepository.findById(dto.getTorreId())
                .orElseThrow(() -> new EntityNotFoundException("Torre no encontrada"));
        currentUser.assertCondominio(torre.getCondominio().getId());

        entity.setNumero(dto.getNumero());
        entity.setTorre(torre);

        return mapToDTO(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        Piso entity = repository.findByIdWithRelations(id)
                .orElseThrow(() -> new EntityNotFoundException("Piso no encontrado"));
        currentUser.assertCondominio(entity.getTorre().getCondominio().getId());
        repository.delete(entity);
    }

    private PisoResponseDTO mapToDTO(Piso entity) {
        return PisoResponseDTO.builder()
                .id(entity.getId())
                .numero(entity.getNumero())
                .torreId(entity.getTorre().getId())
                .torreNombre(entity.getTorre().getNombre())
                .condominioId(entity.getTorre().getCondominio().getId())
                .build();
    }
}
