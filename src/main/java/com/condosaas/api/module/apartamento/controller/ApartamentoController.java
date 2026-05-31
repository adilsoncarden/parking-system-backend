package com.condosaas.api.module.apartamento.controller;

import com.condosaas.api.module.apartamento.dto.*;
import com.condosaas.api.module.apartamento.service.ApartamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/apartamentos")
@RequiredArgsConstructor
public class ApartamentoController {

    private final ApartamentoService service;

    @PostMapping("/create")
    public ResponseEntity<ApartamentoResponseDTO> create(@RequestBody ApartamentoRequestDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("")
    public ResponseEntity<List<ApartamentoResponseDTO>> getAll(
            @RequestParam(required = false) Long pisoId) {
        return ResponseEntity.ok(service.getAll(pisoId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApartamentoResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ApartamentoResponseDTO> update(@PathVariable Long id,
            @RequestBody ApartamentoRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}