package com.condosaas.api.module.estacionamiento.controller;

import com.condosaas.api.module.estacionamiento.dto.*;
import com.condosaas.api.module.estacionamiento.service.EstacionamientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estacionamiento")
@RequiredArgsConstructor
public class EstacionamientoController {

    private final EstacionamientoService service;

    @PostMapping("/create")
    public ResponseEntity<EstacionamientoResponseDTO> create(@RequestBody EstacionamientoRequestDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("")
    public ResponseEntity<List<EstacionamientoResponseDTO>> getAll(
            @RequestParam(required = false) Long zonaId) {
        return ResponseEntity.ok(service.getAll(zonaId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstacionamientoResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<EstacionamientoResponseDTO> update(@PathVariable Long id,
            @RequestBody EstacionamientoRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}