package com.condosaas.api.module.carrito_carga.service;

import com.condosaas.api.module.carrito_carga.dto.*;

import java.util.List;

public interface CarritoCargaService {

    CarritoCargaResponseDTO create(CarritoCargaRequestDTO dto);

    CarritoCargaResponseDTO getById(Long id);

    List<CarritoCargaResponseDTO> getAll(Long condominioId);

    CarritoCargaResponseDTO update(Long id, CarritoCargaRequestDTO dto);

    void delete(Long id);
}