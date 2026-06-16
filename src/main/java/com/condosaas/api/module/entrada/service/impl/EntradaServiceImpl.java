package com.condosaas.api.module.entrada.service.impl;

import com.condosaas.api.exception.BusinessRuleException;
import com.condosaas.api.module.condominio.model.Condominio;
import com.condosaas.api.module.condominio.repository.CondominioRepository;
import com.condosaas.api.module.entrada.dto.*;
import com.condosaas.api.module.entrada.model.Entrada;
import com.condosaas.api.module.entrada.repository.EntradaRepository;
import com.condosaas.api.module.entrada.service.EntradaService;
import com.condosaas.api.config.CondominioProperties;
import com.condosaas.api.security.CurrentUser;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EntradaServiceImpl implements EntradaService {

    private final EntradaRepository repository;
    private final CondominioRepository condominioRepository;
    private final CurrentUser currentUser;
    // Máximo de entradas (puertas) por condominio, configurable (app.condominio.max-entradas, def. 2).
    private final CondominioProperties condominioProperties;

    @Override
    public EntradaResponseDTO create(EntradaRequestDTO dto) {

        currentUser.assertCondominio(dto.getCondominioId());
        Condominio condominio = condominioRepository.findById(dto.getCondominioId())
                .orElseThrow(() -> new EntityNotFoundException("Condominio no encontrado"));

        if (repository.countByCondominioId(condominio.getId()) >= condominioProperties.getMaxEntradas()) {
            throw new BusinessRuleException(
                    "Un condominio puede tener máximo " + condominioProperties.getMaxEntradas() + " entradas.");
        }

        Entrada entity = Entrada.builder()
                .nombre(dto.getNombre())
                .capacidadCarritos(dto.getCapacidadCarritos())
                .condominio(condominio)
                .build();

        return mapToDTO(repository.save(entity));
    }

    @Override
    public EntradaResponseDTO getById(Long id) {
        Entrada entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entrada no encontrada"));
        currentUser.assertCondominio(entity.getCondominio().getId());

        return mapToDTO(entity);
    }

    @Override
    public List<EntradaResponseDTO> getAll(Long condominioId) {

        Long scoped = currentUser.resolveFilter(condominioId);

        List<Entrada> lista = (scoped != null)
                ? repository.findByCondominioId(scoped)
                : repository.findAll();

        return lista.stream().map(this::mapToDTO).toList();
    }

    @Override
    public EntradaResponseDTO update(Long id, EntradaRequestDTO dto) {

        Entrada entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entrada no encontrada"));
        currentUser.assertCondominio(entity.getCondominio().getId());
        currentUser.assertCondominio(dto.getCondominioId());

        Condominio condominio = condominioRepository.findById(dto.getCondominioId())
                .orElseThrow(() -> new EntityNotFoundException("Condominio no encontrado"));

        // Si se cambia de condominio, validar el límite en el destino.
        boolean cambiaCondominio = !entity.getCondominio().getId().equals(condominio.getId());
        if (cambiaCondominio
                && repository.countByCondominioId(condominio.getId()) >= condominioProperties.getMaxEntradas()) {
            throw new BusinessRuleException(
                    "Un condominio puede tener máximo " + condominioProperties.getMaxEntradas() + " entradas.");
        }

        entity.setNombre(dto.getNombre());
        entity.setCapacidadCarritos(dto.getCapacidadCarritos());
        entity.setCondominio(condominio);

        return mapToDTO(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        Entrada entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entrada no encontrada"));
        currentUser.assertCondominio(entity.getCondominio().getId());
        repository.delete(entity);
    }

    private EntradaResponseDTO mapToDTO(Entrada entity) {
        return EntradaResponseDTO.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .capacidadCarritos(entity.getCapacidadCarritos())
                .condominioId(entity.getCondominio().getId())
                .condominioNombre(entity.getCondominio().getNombre())
                .build();
    }
}
