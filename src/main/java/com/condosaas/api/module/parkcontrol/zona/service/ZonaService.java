package com.condosaas.api.module.parkcontrol.zona.service;

import com.condosaas.api.module.parkcontrol.zona.dto.ZonaRequest;
import com.condosaas.api.module.parkcontrol.zona.dto.ZonaResponse;
import java.util.List;

public interface ZonaService {
    List<ZonaResponse> findAll(Long condominioId);

    ZonaResponse findById(Long id);

    ZonaResponse create(ZonaRequest request);

    ZonaResponse update(Long id, ZonaRequest request);

    void delete(Long id);
}
