package com.condosaas.api.module.parkcontrol.pase_invitado.repository;

import com.condosaas.api.module.parkcontrol.pase_invitado.model.PaseInvitado;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PaseInvitadoRepository extends JpaRepository<PaseInvitado, Long> {
    List<PaseInvitado> findByCondominioId(Long condominioId);

    List<PaseInvitado> findByActivoTrue();
}
