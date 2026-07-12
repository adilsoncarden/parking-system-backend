package com.condosaas.api.module.configuracion_multa.service.impl;

import com.condosaas.api.config.CarritoPenalizacionProperties;
import com.condosaas.api.module.condominio.model.Condominio;
import com.condosaas.api.module.condominio.repository.CondominioRepository;
import com.condosaas.api.module.configuracion_multa.dto.*;
import com.condosaas.api.module.configuracion_multa.model.ConfiguracionMulta;
import com.condosaas.api.module.configuracion_multa.repository.ConfiguracionMultaRepository;
import com.condosaas.api.module.configuracion_multa.service.ConfiguracionMultaService;
import com.condosaas.api.security.CurrentUser;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ConfiguracionMultaServiceImpl implements ConfiguracionMultaService {

    private final ConfiguracionMultaRepository repository;
    private final CondominioRepository condominioRepository;
    private final CarritoPenalizacionProperties defaults;
    private final CurrentUser currentUser;

    @Override
    @Transactional(readOnly = true)
    public List<ConfiguracionMultaResponseDTO> getAll() {
        List<ConfiguracionMulta> lista;
        if (currentUser.isScoped()) {
            lista = repository.findByCondominioId(currentUser.condominioId())
                    .map(List::of).orElseGet(List::of);
        } else {
            lista = repository.findAll();
        }
        return lista.stream().map(this::mapToDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ConfiguracionMultaResponseDTO getByCondominio(Long condominioId) {
        currentUser.assertCondominio(condominioId);
        return repository.findByCondominioId(condominioId)
                .map(this::mapToDTO)
                .orElseGet(() -> defaultDTO(condominioId));
    }

    @Override
    public ConfiguracionMultaResponseDTO upsert(ConfiguracionMultaRequestDTO dto) {
        currentUser.assertCondominio(dto.getCondominioId());
        Condominio condominio = condominioRepository.findById(dto.getCondominioId())
                .orElseThrow(() -> new EntityNotFoundException("Condominio no encontrado"));

        ConfiguracionMulta entity = repository.findByCondominioId(dto.getCondominioId())
                .orElseGet(() -> ConfiguracionMulta.builder().condominio(condominio).build());
        entity.setCondominio(condominio);
        entity.setTiempoLimiteMinutos(dto.getTiempoLimiteMinutos());
        entity.setTarifaPorMinuto(dto.getTarifaPorMinuto());
        return mapToDTO(repository.save(entity));
    }

    @Override
    public void delete(Long condominioId) {
        currentUser.assertCondominio(condominioId);
        repository.findByCondominioId(condominioId).ifPresent(repository::delete);
    }

    @Override
    @Transactional(readOnly = true)
    public int limiteMinutos(Long condominioId) {
        return repository.findByCondominioId(condominioId)
                .map(ConfiguracionMulta::getTiempoLimiteMinutos)
                .orElse(defaults.getTiempoLimiteMinutos());
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal tarifaPorMinuto(Long condominioId) {
        return repository.findByCondominioId(condominioId)
                .map(ConfiguracionMulta::getTarifaPorMinuto)
                .orElse(defaults.getTarifaPorMinuto());
    }

    private ConfiguracionMultaResponseDTO defaultDTO(Long condominioId) {
        String nombre = condominioRepository.findById(condominioId)
                .map(Condominio::getNombre).orElse(null);
        return ConfiguracionMultaResponseDTO.builder()
                .id(null)
                .condominioId(condominioId)
                .condominioNombre(nombre)
                .tiempoLimiteMinutos(defaults.getTiempoLimiteMinutos())
                .tarifaPorMinuto(defaults.getTarifaPorMinuto())
                .porDefecto(true)
                .build();
    }

    private ConfiguracionMultaResponseDTO mapToDTO(ConfiguracionMulta e) {
        return ConfiguracionMultaResponseDTO.builder()
                .id(e.getId())
                .condominioId(e.getCondominio().getId())
                .condominioNombre(e.getCondominio().getNombre())
                .tiempoLimiteMinutos(e.getTiempoLimiteMinutos())
                .tarifaPorMinuto(e.getTarifaPorMinuto())
                .porDefecto(false)
                .build();
    }
}
