package com.condosaas.api.module.parkcontrol.vehiculo.repository;

import com.condosaas.api.module.parkcontrol.vehiculo.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {
    List<Vehiculo> findByCondominioId(Long condominioId);
}
