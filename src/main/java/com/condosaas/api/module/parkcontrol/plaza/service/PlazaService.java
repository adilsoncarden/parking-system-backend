package com.condosaas.api.module.parkcontrol.plaza.service;

import com.condosaas.api.module.enums.EstadoOcupacion;
import com.condosaas.api.module.parkcontrol.plaza.dto.PlazaRequest;
import com.condosaas.api.module.parkcontrol.plaza.dto.PlazaResponse;
import java.util.List;

public interface PlazaService {
    List<PlazaResponse> findAll(Long idZona, Long condominioId);

    PlazaResponse findById(Long id);

    PlazaResponse create(PlazaRequest request);

    PlazaResponse update(Long id, PlazaRequest request);

    PlazaResponse cambiarEstado(Long id, EstadoOcupacion estado);

    void delete(Long id);
}
