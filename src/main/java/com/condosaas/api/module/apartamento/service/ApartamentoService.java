package com.condosaas.api.module.apartamento.service;

import com.condosaas.api.module.apartamento.dto.ApartamentoRequest;
import com.condosaas.api.module.apartamento.dto.ApartamentoResponse;
import java.util.List;

public interface ApartamentoService {
    List<ApartamentoResponse> findAll(Long pisoId);

    ApartamentoResponse findById(Long id);

    ApartamentoResponse create(ApartamentoRequest request);

    ApartamentoResponse update(Long id, ApartamentoRequest request);

    void delete(Long id);
}
