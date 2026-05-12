package com.condosaas.api.torres.service;

import com.condosaas.api.condominio.Repository.CondominioRepository;
import com.condosaas.api.condominio.entity.Condominio;
import com.condosaas.api.torres.dto.TorreRequest;
import com.condosaas.api.torres.entity.Torres;
import com.condosaas.api.torres.repository.TorresRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TorresService {

    private final TorresRepository torresRepository;
    private final CondominioRepository condominioRepository;

    public List<Torres> ListarTodasLasTorresPorCondominio() {
        return torresRepository.findAll();

    }

    public Torres guardarTorre(TorreRequest request) {
        // 1. Buscamos el condominio real
        Condominio con = condominioRepository.findById(request.getIdCondominio())
                .orElseThrow(() -> new RuntimeException("Condominio no encontrado"));
        // 2. Mapeamos del DTO a la Entidad
        Torres torre = new Torres();
        torre.setNombre(request.getNombre());
        // Ojo con el nombre en tu entidad
        torre.setCondominio(con);
        return torresRepository.save(torre);
    }

      public void eliminar (Torres torres){
          torresRepository.delete(torres);
     }






}