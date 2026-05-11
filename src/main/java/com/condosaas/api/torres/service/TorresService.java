package com.condosaas.api.torres.service;

import com.condosaas.api.condominio.Repository.CondominioRepository;
import com.condosaas.api.torres.entity.Torres;
import com.condosaas.api.torres.repository.TorresRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TorresService {

    private final TorresRepository torresRepository;
    private final  CondominioRepository condominioRepository;

 public List<Torres> ListarTodasLasTorresPorCondominio () {
     return torresRepository.findAll();

 }

















}
