package com.condosaas.api.module.parkcontrol.vehiculo.service;

import com.condosaas.api.module.parkcontrol.vehiculo.dto.VehiculoRequest;
import com.condosaas.api.module.parkcontrol.vehiculo.dto.VehiculoResponse;
import java.util.List;

public interface VehiculoService {
    List<VehiculoResponse> findAll(Long condominioId);

    VehiculoResponse findById(Long id);

    VehiculoResponse create(VehiculoRequest request);

    VehiculoResponse update(Long id, VehiculoRequest request);

    void delete(Long id);
}
