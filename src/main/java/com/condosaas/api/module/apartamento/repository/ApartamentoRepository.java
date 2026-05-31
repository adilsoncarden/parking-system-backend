package com.condosaas.api.module.apartamento.repository;

import com.condosaas.api.module.apartamento.model.Apartamento;
import com.condosaas.api.module.apartamento.model.EstadoApartamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ApartamentoRepository extends JpaRepository<Apartamento, Long> {

    long countByEstado(EstadoApartamento estado);

    @Query("""
            SELECT a FROM Apartamento a
            JOIN FETCH a.piso p
            JOIN FETCH p.torre t
            JOIN FETCH t.condominio
            """)
    List<Apartamento> findAllWithRelations();

    @Query("""
            SELECT a FROM Apartamento a
            JOIN FETCH a.piso p
            JOIN FETCH p.torre t
            JOIN FETCH t.condominio
            WHERE p.id = :pisoId
            """)
    List<Apartamento> findByPisoIdWithRelations(@Param("pisoId") Long pisoId);

    @Query("""
            SELECT a FROM Apartamento a
            JOIN FETCH a.piso p
            JOIN FETCH p.torre t
            JOIN FETCH t.condominio
            WHERE a.id = :id
            """)
    Optional<Apartamento> findByIdWithRelations(@Param("id") Long id);
}
