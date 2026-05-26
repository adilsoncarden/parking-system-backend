package com.condosaas.api.entrada.repository;

import com.condosaas.api.entrada.entity.EntradaSalida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntradaSalidaRepository extends JpaRepository<EntradaSalida, Long> {

    List<EntradaSalida> findByCondominioId(Long idCondominio);
}
