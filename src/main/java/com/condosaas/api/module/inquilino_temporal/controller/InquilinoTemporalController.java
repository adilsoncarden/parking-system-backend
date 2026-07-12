package com.condosaas.api.module.inquilino_temporal.controller;

import com.condosaas.api.module.inquilino_temporal.dto.*;
import com.condosaas.api.module.inquilino_temporal.service.InquilinoTemporalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inquilinos-temporales")
@RequiredArgsConstructor
public class InquilinoTemporalController {

    private final InquilinoTemporalService service;

    @PostMapping("/create")
    public ResponseEntity<InquilinoTemporalResponseDTO> create(
            @Valid @RequestBody InquilinoTemporalRequestDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("")
    public ResponseEntity<List<InquilinoTemporalResponseDTO>> getAll(
            @RequestParam(required = false) Long estacionamientoId) {
        return ResponseEntity.ok(service.getAll(estacionamientoId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InquilinoTemporalResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<InquilinoTemporalResponseDTO> update(@PathVariable Long id,
            @Valid @RequestBody InquilinoTemporalRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
