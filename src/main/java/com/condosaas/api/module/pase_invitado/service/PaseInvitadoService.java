package com.condosaas.api.module.pase_invitado.service;

import com.condosaas.api.module.pase_invitado.dto.*;

import java.util.List;

public interface PaseInvitadoService {

    PaseInvitadoResponseDTO create(PaseInvitadoRequestDTO dto);

    PaseInvitadoResponseDTO getById(Long id);

    List<PaseInvitadoResponseDTO> getAll(Long usuarioId);

    PaseInvitadoResponseDTO update(Long id, PaseInvitadoRequestDTO dto);

    void delete(Long id);
}