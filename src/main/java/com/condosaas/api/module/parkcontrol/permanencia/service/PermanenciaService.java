package com.condosaas.api.module.parkcontrol.permanencia.service;

import com.condosaas.api.module.parkcontrol.permanencia.dto.PermanenciaRequest;
import com.condosaas.api.module.parkcontrol.permanencia.dto.PermanenciaResponse;
import java.util.List;

public interface PermanenciaService {
    List<PermanenciaResponse> findAll(Long condominioId);

    PermanenciaResponse findById(Long id);

    PermanenciaResponse calcular(PermanenciaRequest request);

    void delete(Long id);
}
