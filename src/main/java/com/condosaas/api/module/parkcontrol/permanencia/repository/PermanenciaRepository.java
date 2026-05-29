package com.condosaas.api.module.parkcontrol.permanencia.repository;

import com.condosaas.api.module.parkcontrol.permanencia.model.Permanencia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PermanenciaRepository extends JpaRepository<Permanencia, Long> {
    List<Permanencia> findByAccesoVehiculoCondominioId(Long condominioId);

    Optional<Permanencia> findByAccesoId(Long accesoId);
}
