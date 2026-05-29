package com.condosaas.api.module.torre.service;

import com.condosaas.api.module.torre.dto.TorreRequest;
import com.condosaas.api.module.torre.dto.TorreResponse;
import java.util.List;

public interface TorreService {
    List<TorreResponse> findAll(Long condominioId);

    TorreResponse findById(Long id);

    TorreResponse create(TorreRequest request);

    TorreResponse update(Long id, TorreRequest request);

    void delete(Long id);
}
