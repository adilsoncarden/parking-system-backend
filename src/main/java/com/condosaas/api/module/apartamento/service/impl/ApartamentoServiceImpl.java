package com.condosaas.api.module.apartamento.service.impl;

import com.condosaas.api.module.apartamento.dto.*;
import com.condosaas.api.module.apartamento.model.*;
import com.condosaas.api.module.apartamento.repository.ApartamentoRepository;
import com.condosaas.api.module.apartamento.service.ApartamentoService;
import com.condosaas.api.module.piso.model.Piso;
import com.condosaas.api.module.piso.repository.PisoRepository;
import com.condosaas.api.security.CurrentUser;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ApartamentoServiceImpl implements ApartamentoService {

    private final ApartamentoRepository repository;
    private final PisoRepository pisoRepository;
    private final CurrentUser currentUser;

    private Long condominioDe(Piso piso) {
        return piso.getTorre().getCondominio().getId();
    }

    @Override
    public ApartamentoResponseDTO create(ApartamentoRequestDTO dto) {

        Piso piso = pisoRepository.findByIdWithRelations(dto.getPisoId())
                .orElseThrow(() -> new EntityNotFoundException("Piso no encontrado"));
        currentUser.assertCondominio(condominioDe(piso));

        Apartamento entity = Apartamento.builder()
                .numero(dto.getNumero())
                .area(dto.getArea())
                .estado(dto.getEstado())
                .piso(piso)
                .build();

        return mapToDTO(repository.save(entity));
    }

    @Override
    public ApartamentoResponseDTO getById(Long id) {
        Apartamento entity = repository.findByIdWithRelations(id)
                .orElseThrow(() -> new EntityNotFoundException("Apartamento no encontrado"));
        currentUser.assertCondominio(condominioDe(entity.getPiso()));

        return mapToDTO(entity);
    }

    @Override
    public List<ApartamentoResponseDTO> getAll(Long pisoId, Long condominioId) {

        List<Apartamento> lista;
        if (currentUser.isScoped()) {
            lista = repository.findByCondominioIdWithRelations(currentUser.condominioId());
        } else if (condominioId != null) {
            lista = repository.findByCondominioIdWithRelations(condominioId);
        } else if (pisoId != null) {
            lista = repository.findByPisoIdWithRelations(pisoId);
        } else {
            lista = repository.findAllWithRelations();
        }

        return lista.stream().map(this::mapToDTO).toList();
    }

    @Override
    public ApartamentoResponseDTO update(Long id, ApartamentoRequestDTO dto) {

        Apartamento entity = repository.findByIdWithRelations(id)
                .orElseThrow(() -> new EntityNotFoundException("Apartamento no encontrado"));
        currentUser.assertCondominio(condominioDe(entity.getPiso()));

        Piso piso = pisoRepository.findByIdWithRelations(dto.getPisoId())
                .orElseThrow(() -> new EntityNotFoundException("Piso no encontrado"));
        currentUser.assertCondominio(condominioDe(piso));

        entity.setNumero(dto.getNumero());
        entity.setArea(dto.getArea());
        entity.setEstado(dto.getEstado());
        entity.setPiso(piso);

        return mapToDTO(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        Apartamento entity = repository.findByIdWithRelations(id)
                .orElseThrow(() -> new EntityNotFoundException("Apartamento no encontrado"));
        currentUser.assertCondominio(condominioDe(entity.getPiso()));
        repository.delete(entity);
    }

    private ApartamentoResponseDTO mapToDTO(Apartamento entity) {
        return ApartamentoResponseDTO.builder()
                .id(entity.getId())
                .numero(entity.getNumero())
                .area(entity.getArea())
                .estado(entity.getEstado())
                .pisoId(entity.getPiso().getId())
                .pisoNumero(entity.getPiso().getNumero())
                .torreId(entity.getPiso().getTorre().getId())
                .torreNombre(entity.getPiso().getTorre().getNombre())
                .condominioId(entity.getPiso().getTorre().getCondominio().getId())
                .build();
    }
}
