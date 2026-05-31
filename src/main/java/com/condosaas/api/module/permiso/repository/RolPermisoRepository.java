package com.condosaas.api.module.permiso.repository;

import com.condosaas.api.module.permiso.model.RolPermiso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RolPermisoRepository extends JpaRepository<RolPermiso, Long> {

    List<RolPermiso> findByRolId(Long rolId);

    void deleteByRolId(Long rolId);

    void deleteByPermiso_Id(Long permisoId);

    @Query("SELECT rp.permiso.nombre FROM RolPermiso rp WHERE rp.rol.id = :rolId")
    List<String> findPermisoNombresByRolId(@Param("rolId") Long rolId);
}
