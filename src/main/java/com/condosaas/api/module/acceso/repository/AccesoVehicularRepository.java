package com.condosaas.api.module.acceso.repository;

import com.condosaas.api.module.acceso.model.AccesoVehicular;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AccesoVehicularRepository extends JpaRepository<AccesoVehicular, Long> {
    List<AccesoVehicular> findByCondominioId(Long condominioId);

    List<AccesoVehicular> findByPlaca(String placa);
}
