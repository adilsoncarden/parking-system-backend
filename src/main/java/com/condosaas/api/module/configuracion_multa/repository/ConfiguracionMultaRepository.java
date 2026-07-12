package com.condosaas.api.module.configuracion_multa.repository;

import com.condosaas.api.module.configuracion_multa.model.ConfiguracionMulta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfiguracionMultaRepository extends JpaRepository<ConfiguracionMulta, Long> {

    Optional<ConfiguracionMulta> findByCondominioId(Long condominioId);
}
