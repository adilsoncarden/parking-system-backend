package com.condosaas.api.module.rol.service;

import com.condosaas.api.module.rol.dto.RolRequest;
import com.condosaas.api.module.rol.dto.RolResponse;
import java.util.List;

public interface RolService {
    List<RolResponse> findAll();

    RolResponse findById(Integer id);

    RolResponse create(RolRequest request);

    RolResponse update(Integer id, RolRequest request);

    void delete(Integer id);
}
