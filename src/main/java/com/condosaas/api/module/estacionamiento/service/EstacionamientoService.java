package com.condosaas.api.module.estacionamiento.service;

import com.condosaas.api.module.enums.EstadoOcupacion;
import com.condosaas.api.module.estacionamiento.dto.EstacionamientoRequest;
import com.condosaas.api.module.estacionamiento.dto.EstacionamientoResponse;
import java.util.List;

public interface EstacionamientoService {
    List<EstacionamientoResponse> findAll(Long condominioId);
    EstacionamientoResponse findById(Long id);
    EstacionamientoResponse create(EstacionamientoRequest request);
    EstacionamientoResponse update(Long id, EstacionamientoRequest request);
    EstacionamientoResponse updateEstado(Long id, EstadoOcupacion estado);
    void delete(Long id);
}
