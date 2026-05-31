package com.condosaas.api.module.condominio.repository;

import com.condosaas.api.module.condominio.model.Condominio;
import com.condosaas.api.module.condominio.model.EstadoCondominio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CondominioRepository extends JpaRepository<Condominio, Long> {

    List<Condominio> findByEstado(EstadoCondominio estado);
}