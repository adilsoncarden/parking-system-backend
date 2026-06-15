package com.condosaas.api.module.usuario.repository;

import com.condosaas.api.module.usuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("SELECT u FROM Usuario u JOIN FETCH u.rol LEFT JOIN FETCH u.condominio WHERE u.email = :email")
    Optional<Usuario> findByEmail(@Param("email") String email);

    @Query("""
            SELECT u FROM Usuario u
            JOIN FETCH u.rol
            LEFT JOIN FETCH u.apartamento
            LEFT JOIN FETCH u.condominio
            LEFT JOIN FETCH u.entrada
            """)
    List<Usuario> findAllWithRol();

    @Query("""
            SELECT DISTINCT u FROM Usuario u
            JOIN FETCH u.rol
            LEFT JOIN FETCH u.apartamento ap
            LEFT JOIN FETCH u.condominio
            LEFT JOIN FETCH u.entrada
            LEFT JOIN ap.piso p
            LEFT JOIN p.torre t
            WHERE u.condominio.id = :condominioId OR t.condominio.id = :condominioId
            """)
    List<Usuario> findAllByCondominioScope(@Param("condominioId") Long condominioId);

    @Query("""
            SELECT u FROM Usuario u
            JOIN FETCH u.rol
            LEFT JOIN FETCH u.apartamento
            LEFT JOIN FETCH u.condominio
            LEFT JOIN FETCH u.entrada
            WHERE u.rol.id = :rolId
            """)
    List<Usuario> findByRolIdWithRol(@Param("rolId") Long rolId);

    @Query("""
            SELECT u FROM Usuario u
            JOIN FETCH u.rol
            LEFT JOIN FETCH u.apartamento
            LEFT JOIN FETCH u.condominio
            LEFT JOIN FETCH u.entrada
            WHERE u.apartamento.id = :apartamentoId
            """)
    List<Usuario> findByApartamentoIdWithRol(@Param("apartamentoId") Long apartamentoId);

    @Query("""
            SELECT u FROM Usuario u
            JOIN FETCH u.rol
            LEFT JOIN FETCH u.apartamento
            LEFT JOIN FETCH u.condominio
            LEFT JOIN FETCH u.entrada
            WHERE u.id = :id
            """)
    Optional<Usuario> findByIdWithRol(@Param("id") Long id);

    List<Usuario> findByRolId(Long rolId);

    List<Usuario> findByApartamentoId(Long apartamentoId);
}
