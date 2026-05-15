package com.condosaas.api.torres.Controller;

import com.condosaas.api.torres.dto.TorreRequest;
import com.condosaas.api.torres.entity.Torres;
import com.condosaas.api.torres.service.TorresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/torres")
public class TorresController {
    @Autowired
    private TorresService torresService;

    @GetMapping
    public ResponseEntity<List<Torres>> obtenerTodas() {
        List<Torres> torres = torresService.ListarTodasLasTorresPorCondominio();
        return ResponseEntity.ok(torres);
    }

    @PostMapping
    public ResponseEntity<Torres> Guardar(@RequestBody TorreRequest request) {
        return ResponseEntity.ok(torresService.guardarTorre(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@RequestBody Torres torres) {
        torresService.eliminar(torres);
        return ResponseEntity.noContent().build();
    }

}
