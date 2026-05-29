package com.condosaas.api.module.parkcontrol.acceso.repository;

import com.condosaas.api.module.parkcontrol.acceso.model.Acceso;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AccesoRepository extends JpaRepository<Acceso, Long> {
    List<Acceso> findByVehiculoCondominioId(Long condominioId);

    List<Acceso> findByVehiculoId(Long idVehiculo);
}
