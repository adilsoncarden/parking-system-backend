package com.condosaas.api.module.carrito_carga.controller;

import com.condosaas.api.module.carrito_carga.dto.*;
import com.condosaas.api.module.carrito_carga.service.CarritoCargaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carritos")
@RequiredArgsConstructor
public class CarritoCargaController {

    private final CarritoCargaService service;

    @PostMapping("/create")
    public ResponseEntity<CarritoCargaResponseDTO> create(@RequestBody CarritoCargaRequestDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("")
    public ResponseEntity<List<CarritoCargaResponseDTO>> getAll(
            @RequestParam(required = false) Long condominioId) {
        return ResponseEntity.ok(service.getAll(condominioId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarritoCargaResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<CarritoCargaResponseDTO> update(@PathVariable Long id,
            @RequestBody CarritoCargaRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}