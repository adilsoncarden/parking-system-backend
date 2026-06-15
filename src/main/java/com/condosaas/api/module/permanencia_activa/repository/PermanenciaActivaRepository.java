package com.condosaas.api.module.permanencia_activa.repository;

import com.condosaas.api.module.permanencia_activa.model.EstadoPermanencia;
import com.condosaas.api.module.permanencia_activa.model.PermanenciaActiva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PermanenciaActivaRepository extends JpaRepository<PermanenciaActiva, Long> {

    List<PermanenciaActiva> findByVehiculoId(Long vehiculoId);

    // Permanencias de un condominio (vía vehículo→usuario→...→condominio) para el admin scoped.
    @Query("""
            SELECT p FROM PermanenciaActiva p
            WHERE p.vehiculo.usuario.apartamento.piso.torre.condominio.id = :condominioId
            """)
    List<PermanenciaActiva> findByCondominioId(Long condominioId);

    Optional<PermanenciaActiva> findByVehiculoIdAndEstado(Long vehiculoId, String estado);

    Optional<PermanenciaActiva> findFirstByVehiculoIdAndEstado(Long vehiculoId, EstadoPermanencia estado);

    long countByEstado(EstadoPermanencia estado);
}