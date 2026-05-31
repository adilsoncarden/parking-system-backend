package com.condosaas.api.module.pase_invitado.repository;

import com.condosaas.api.module.pase_invitado.model.PaseInvitado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaseInvitadoRepository extends JpaRepository<PaseInvitado, Long> {

    Optional<PaseInvitado> findByCodigo(String codigo);

    List<PaseInvitado> findByUsuarioId(Long usuarioId);
}