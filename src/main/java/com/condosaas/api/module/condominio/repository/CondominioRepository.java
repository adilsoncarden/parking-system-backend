package com.condosaas.api.module.condominio.repository;

import com.condosaas.api.module.condominio.model.Condominio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CondominioRepository extends JpaRepository<Condominio, Long> {
}
