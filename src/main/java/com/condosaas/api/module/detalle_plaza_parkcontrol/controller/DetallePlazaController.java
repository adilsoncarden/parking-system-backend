package com.condosaas.api.module.detalle_plaza_parkcontrol.controller;

import com.condosaas.api.module.detalle_plaza_parkcontrol.dto.*;
import com.condosaas.api.module.detalle_plaza_parkcontrol.service.DetallePlazaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalles-plaza")
@RequiredArgsConstructor
public class DetallePlazaController {

    private final DetallePlazaService service;

    @PostMapping("/create")
    public ResponseEntity<DetallePlazaResponseDTO> create(@RequestBody DetallePlazaRequestDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("")
    public ResponseEntity<List<DetallePlazaResponseDTO>> getAll(
            @RequestParam(required = false) Long estacionamientoId) {
        return ResponseEntity.ok(service.getAll(estacionamientoId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetallePlazaResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<DetallePlazaResponseDTO> update(@PathVariable Long id,
            @RequestBody DetallePlazaRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}