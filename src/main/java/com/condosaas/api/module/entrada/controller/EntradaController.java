package com.condosaas.api.module.entrada.controller;

import com.condosaas.api.module.entrada.dto.*;
import com.condosaas.api.module.entrada.service.EntradaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entradas")
@RequiredArgsConstructor
public class EntradaController {

    private final EntradaService service;

    @PostMapping("/create")
    public ResponseEntity<EntradaResponseDTO> create(@Valid @RequestBody EntradaRequestDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("")
    public ResponseEntity<List<EntradaResponseDTO>> getAll(
            @RequestParam(required = false) Long condominioId) {
        return ResponseEntity.ok(service.getAll(condominioId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntradaResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<EntradaResponseDTO> update(@PathVariable Long id,
            @Valid @RequestBody EntradaRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
