package com.condosaas.api.module.usuario.repository;

import com.condosaas.api.module.usuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    List<Usuario> findByRolId(Long rolId);

    List<Usuario> findByApartamentoId(Long apartamentoId);

}