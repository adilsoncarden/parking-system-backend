package com.condosaas.api.module.permanencia_activa.controller;

import com.condosaas.api.module.permanencia_activa.dto.*;
import com.condosaas.api.module.permanencia_activa.service.PermanenciaActivaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permanencias")
@RequiredArgsConstructor
public class PermanenciaActivaController {

    private final PermanenciaActivaService service;

    @PostMapping("/create")
    public ResponseEntity<PermanenciaActivaResponseDTO> create(@RequestBody PermanenciaActivaRequestDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    // Flujo de negocio: registra el ingreso de un vehículo (crea log ENTRADA + permanencia + ocupa plaza).
    @PostMapping("/registrar-entrada")
    public ResponseEntity<PermanenciaActivaResponseDTO> registrarEntrada(
            @Valid @RequestBody RegistrarEntradaRequestDTO dto) {
        return ResponseEntity.ok(service.registrarEntrada(dto));
    }

    // Flujo de negocio: registra la salida (crea log SALIDA + finaliza permanencia + libera plaza).
    @PostMapping("/registrar-salida")
    public ResponseEntity<PermanenciaActivaResponseDTO> registrarSalida(
            @Valid @RequestBody RegistrarSalidaRequestDTO dto) {
        return ResponseEntity.ok(service.registrarSalida(dto));
    }

    @GetMapping("")
    public ResponseEntity<List<PermanenciaActivaResponseDTO>> getAll(
            @RequestParam(required = false) Long vehiculoId) {
        return ResponseEntity.ok(service.getAll(vehiculoId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PermanenciaActivaResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<PermanenciaActivaResponseDTO> update(@PathVariable Long id,
            @RequestBody PermanenciaActivaRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}