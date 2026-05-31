package com.condosaas.api.module.vehiculo.controller;

import com.condosaas.api.module.vehiculo.dto.*;
import com.condosaas.api.module.vehiculo.service.VehiculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
@RequiredArgsConstructor
public class VehiculoController {

    private final VehiculoService service;

    @PostMapping("/create")
    public ResponseEntity<VehiculoResponseDTO> create(@RequestBody VehiculoRequestDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("")
    public ResponseEntity<List<VehiculoResponseDTO>> getAll(
            @RequestParam(required = false) Long usuarioId) {
        return ResponseEntity.ok(service.getAll(usuarioId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehiculoResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<VehiculoResponseDTO> update(@PathVariable Long id,
            @RequestBody VehiculoRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}