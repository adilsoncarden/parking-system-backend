package com.condosaas.api.module.parkcontrol.zona.repository;

import com.condosaas.api.module.parkcontrol.zona.model.Zona;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ZonaRepository extends JpaRepository<Zona, Long> {
    List<Zona> findByCondominioId(Long condominioId);
}
