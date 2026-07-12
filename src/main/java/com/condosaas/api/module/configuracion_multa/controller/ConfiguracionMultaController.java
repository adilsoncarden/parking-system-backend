package com.condosaas.api.module.configuracion_multa.controller;

import com.condosaas.api.module.configuracion_multa.dto.*;
import com.condosaas.api.module.configuracion_multa.service.ConfiguracionMultaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/configuracion-multa")
@RequiredArgsConstructor
public class ConfiguracionMultaController {

    private final ConfiguracionMultaService service;

    @GetMapping("")
    public ResponseEntity<List<ConfiguracionMultaResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/condominio/{condominioId}")
    public ResponseEntity<ConfiguracionMultaResponseDTO> getByCondominio(@PathVariable Long condominioId) {
        return ResponseEntity.ok(service.getByCondominio(condominioId));
    }

    @PutMapping("")
    public ResponseEntity<ConfiguracionMultaResponseDTO> upsert(
            @Valid @RequestBody ConfiguracionMultaRequestDTO dto) {
        return ResponseEntity.ok(service.upsert(dto));
    }

    @DeleteMapping("/condominio/{condominioId}")
    public ResponseEntity<Void> delete(@PathVariable Long condominioId) {
        service.delete(condominioId);
        return ResponseEntity.noContent().build();
    }
}
