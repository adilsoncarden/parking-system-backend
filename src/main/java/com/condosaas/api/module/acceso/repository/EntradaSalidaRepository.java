package com.condosaas.api.module.acceso.repository;

import com.condosaas.api.module.acceso.model.EntradaSalida;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EntradaSalidaRepository extends JpaRepository<EntradaSalida, Long> {
    List<EntradaSalida> findByCondominioId(Long condominioId);
}
