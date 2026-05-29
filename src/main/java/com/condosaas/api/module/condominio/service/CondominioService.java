package com.condosaas.api.module.condominio.service;

import com.condosaas.api.module.condominio.dto.CondominioRequest;
import com.condosaas.api.module.condominio.dto.CondominioResponse;
import java.util.List;

public interface CondominioService {
    List<CondominioResponse> findAll();
    CondominioResponse findById(Long id);
    CondominioResponse create(CondominioRequest request);
    CondominioResponse update(Long id, CondominioRequest request);
    void delete(Long id);
}
