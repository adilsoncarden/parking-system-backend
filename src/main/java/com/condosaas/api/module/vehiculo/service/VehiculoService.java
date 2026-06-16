package com.condosaas.api.module.vehiculo.service;

import com.condosaas.api.module.vehiculo.dto.*;

import java.util.List;

public interface VehiculoService {

    VehiculoResponseDTO create(VehiculoRequestDTO dto);

    VehiculoResponseDTO getById(Long id);

    VehiculoResponseDTO getByPlaca(String placa);

    List<VehiculoResponseDTO> getAll(Long usuarioId);

    VehiculoResponseDTO update(Long id, VehiculoRequestDTO dto);

    void delete(Long id);
}