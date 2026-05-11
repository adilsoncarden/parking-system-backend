package com.condosaas.api.torres.Controller;

import com.condosaas.api.torres.entity.Torres;
import com.condosaas.api.torres.service.TorresService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(name = "admin/torres")
public class TorresController {


    private TorresService torresService;

    @GetMapping
    public ResponseEntity<List<Torres>> obtenerTodas() {
        List<Torres> torres = torresService.ListarTodasLasTorresPorCondominio();
        return ResponseEntity.ok(torres);
    }




}










