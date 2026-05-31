package com.condosaas.api.module.apartamento.service.impl;

import com.condosaas.api.module.apartamento.dto.*;
import com.condosaas.api.module.apartamento.model.*;
import com.condosaas.api.module.apartamento.repository.ApartamentoRepository;
import com.condosaas.api.module.apartamento.service.ApartamentoService;
import com.condosaas.api.module.piso.model.Piso;
import com.condosaas.api.module.piso.repository.PisoRepository;
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

    @Override
    public ApartamentoResponseDTO create(ApartamentoRequestDTO dto) {

        Piso piso = pisoRepository.findById(dto.getPisoId())
                .orElseThrow(() -> new EntityNotFoundException("Piso no encontrado"));

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
        Apartamento entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Apartamento no encontrado"));

        return mapToDTO(entity);
    }

    @Override
    public List<ApartamentoResponseDTO> getAll(Long pisoId) {

        List<Apartamento> lista;

        if (pisoId != null) {
            lista = repository.findByPisoId(pisoId);
        } else {
            lista = repository.findAll();
        }

        return lista.stream().map(this::mapToDTO).toList();
    }

    @Override
    public ApartamentoResponseDTO update(Long id, ApartamentoRequestDTO dto) {

        Apartamento entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Apartamento no encontrado"));

        Piso piso = pisoRepository.findById(dto.getPisoId())
                .orElseThrow(() -> new EntityNotFoundException("Piso no encontrado"));

        entity.setNumero(dto.getNumero());
        entity.setArea(dto.getArea());
        entity.setEstado(dto.getEstado());
        entity.setPiso(piso);

        return mapToDTO(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Apartamento no encontrado");
        }
        repository.deleteById(id);
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