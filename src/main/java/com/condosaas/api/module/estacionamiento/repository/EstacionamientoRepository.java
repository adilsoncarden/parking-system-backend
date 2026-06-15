package com.condosaas.api.module.estacionamiento.repository;

import com.condosaas.api.module.estacionamiento.model.Estacionamiento;
import com.condosaas.api.module.estacionamiento.model.EstadoOcupacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EstacionamientoRepository extends JpaRepository<Estacionamiento, Long> {

    List<Estacionamiento> findByZonaId(Long zonaId);

    // Plazas de un condominio (vía la zona) — para acotar el mapa al admin de condominio.
    List<Estacionamiento> findByZona_Condominio_Id(Long condominioId);

    Optional<Estacionamiento> findByVehiculoActualId(Long vehiculoId);

    long countByEstadoOcupacion(EstadoOcupacion estadoOcupacion);
}