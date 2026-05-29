package com.condosaas.api.module.parkcontrol.acceso.repository;

import com.condosaas.api.module.parkcontrol.acceso.model.Acceso;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface AccesoRepository extends JpaRepository<Acceso, Long> {
    List<Acceso> findByVehiculoCondominioId(Long condominioId);

    List<Acceso> findByVehiculoId(Long idVehiculo);

    Optional<Acceso> findByVehiculoIdAndHoraSalidaIsNull(Long idVehiculo);
}
