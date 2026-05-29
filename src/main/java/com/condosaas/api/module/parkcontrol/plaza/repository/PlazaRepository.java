package com.condosaas.api.module.parkcontrol.plaza.repository;

import com.condosaas.api.module.parkcontrol.plaza.model.Plaza;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PlazaRepository extends JpaRepository<Plaza, Long> {
    List<Plaza> findByZonaId(Long idZona);

    List<Plaza> findByZonaCondominioId(Long condominioId);
}
