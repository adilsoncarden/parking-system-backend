package com.condosaas.api.module.parkcontrol.acceso.service;

import com.condosaas.api.module.parkcontrol.acceso.dto.AccesoRequest;
import com.condosaas.api.module.parkcontrol.acceso.dto.AccesoResponse;
import java.util.List;

public interface AccesoService {
    List<AccesoResponse> findAll(Long condominioId, Long idVehiculo);

    AccesoResponse findById(Long id);

    AccesoResponse registrarEntrada(AccesoRequest request);

    AccesoResponse registrarSalida(Long id);

    void delete(Long id);
}
