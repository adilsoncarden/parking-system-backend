package com.condosaas.api.module.permiso.repository;

import com.condosaas.api.module.permiso.model.Permiso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PermisoRepository extends JpaRepository<Permiso, Long> {

    Optional<Permiso> findByNombre(String nombre);

    List<Permiso> findAllByOrderByNombreAsc();

    boolean existsByNombre(String nombre);
}
