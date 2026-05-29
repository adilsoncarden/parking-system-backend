package com.condosaas.api.module.piso.service;

import com.condosaas.api.module.piso.dto.PisoRequest;
import com.condosaas.api.module.piso.dto.PisoResponse;
import java.util.List;

public interface PisoService {
    List<PisoResponse> findAll(Long torreId);

    PisoResponse findById(Long id);

    PisoResponse create(PisoRequest request);

    PisoResponse update(Long id, PisoRequest request);

    void delete(Long id);
}
