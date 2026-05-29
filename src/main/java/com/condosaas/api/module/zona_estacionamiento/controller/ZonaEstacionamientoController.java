package com.condosaas.api.module.zona_estacionamiento.controller;

import com.condosaas.api.module.zona_estacionamiento.dto.*;
import com.condosaas.api.module.zona_estacionamiento.service.ZonaEstacionamientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/zonas-estacionamiento")
@RequiredArgsConstructor
public class ZonaEstacionamientoController {

    private final ZonaEstacionamientoService service;

    @PostMapping("/create")
    public ResponseEntity<ZonaEstacionamientoResponseDTO> create(@RequestBody ZonaEstacionamientoRequestDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("")
    public ResponseEntity<List<ZonaEstacionamientoResponseDTO>> getAll(
            @RequestParam(required = false) Long condominioId) {
        return ResponseEntity.ok(service.getAll(condominioId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ZonaEstacionamientoResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ZonaEstacionamientoResponseDTO> update(@PathVariable Long id,
            @RequestBody ZonaEstacionamientoRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}