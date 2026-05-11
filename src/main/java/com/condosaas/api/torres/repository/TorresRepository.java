package com.condosaas.api.torres.repository;

import com.condosaas.api.torres.entity.Torres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
//import java.util.List;

@Repository
public interface TorresRepository extends JpaRepository<Torres,Long> {
   //List<Torres> findByCondominioId(Long idCondominio);
}
