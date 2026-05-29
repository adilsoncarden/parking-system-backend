package com.condosaas.api.module.parkcontrol.pase_invitado.service;

import com.condosaas.api.module.parkcontrol.pase_invitado.dto.PaseInvitadoRequest;
import com.condosaas.api.module.parkcontrol.pase_invitado.dto.PaseInvitadoResponse;
import java.util.List;

public interface PaseInvitadoService {
    List<PaseInvitadoResponse> findAll(Long condominioId, Boolean activos);

    PaseInvitadoResponse findById(Long id);

    PaseInvitadoResponse create(PaseInvitadoRequest request);

    PaseInvitadoResponse update(Long id, PaseInvitadoRequest request);

    PaseInvitadoResponse activar(Long id);

    PaseInvitadoResponse desactivar(Long id);

    void delete(Long id);
}
