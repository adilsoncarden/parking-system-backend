package com.condosaas.api.module.vehiculo.repository;

import com.condosaas.api.module.vehiculo.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {

    Optional<Vehiculo> findByPlaca(String placa);

    List<Vehiculo> findByUsuarioId(Long usuarioId);

    @Query("""
            SELECT v FROM Vehiculo v
            JOIN FETCH v.usuario u
            LEFT JOIN FETCH u.apartamento ap
            LEFT JOIN FETCH ap.piso p
            LEFT JOIN FETCH p.torre t
            LEFT JOIN FETCH t.condominio
            """)
    List<Vehiculo> findAllWithUsuario();

    // Igual que findAllWithUsuario pero acotado a un condominio (admins de condominio).
    @Query("""
            SELECT v FROM Vehiculo v
            JOIN FETCH v.usuario u
            LEFT JOIN FETCH u.apartamento ap
            LEFT JOIN FETCH ap.piso p
            LEFT JOIN FETCH p.torre t
            LEFT JOIN FETCH t.condominio c
            WHERE c.id = :condominioId
            """)
    List<Vehiculo> findByCondominioIdWithUsuario(Long condominioId);
}