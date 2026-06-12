package com.condosaas.api.module.piso.repository;

import com.condosaas.api.module.piso.model.Piso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PisoRepository extends JpaRepository<Piso, Long> {

    @Query("""
            SELECT p FROM Piso p
            JOIN FETCH p.torre t
            JOIN FETCH t.condominio
            """)
    List<Piso> findAllWithRelations();

    @Query("""
            SELECT p FROM Piso p
            JOIN FETCH p.torre t
            JOIN FETCH t.condominio
            WHERE t.id = :torreId
            """)
    List<Piso> findByTorreIdWithRelations(@Param("torreId") Long torreId);

    @Query("""
            SELECT p FROM Piso p
            JOIN FETCH p.torre t
            JOIN FETCH t.condominio
            WHERE p.id = :id
            """)
    Optional<Piso> findByIdWithRelations(@Param("id") Long id);

    @Query("""
            SELECT p FROM Piso p
            JOIN FETCH p.torre t
            JOIN FETCH t.condominio c
            WHERE c.id = :condominioId
            """)
    List<Piso> findByCondominioIdWithRelations(@Param("condominioId") Long condominioId);
}
