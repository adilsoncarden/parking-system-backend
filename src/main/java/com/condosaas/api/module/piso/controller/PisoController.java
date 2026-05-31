package com.condosaas.api.module.piso.controller;

import com.condosaas.api.module.piso.dto.*;
import com.condosaas.api.module.piso.service.PisoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pisos")
@RequiredArgsConstructor
public class PisoController {

    private final PisoService service;

    @PostMapping("/create")
    public ResponseEntity<PisoResponseDTO> create(@RequestBody PisoRequestDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("")
    public ResponseEntity<List<PisoResponseDTO>> getAll(
            @RequestParam(required = false) Long torreId) {
        return ResponseEntity.ok(service.getAll(torreId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PisoResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<PisoResponseDTO> update(@PathVariable Long id,
            @RequestBody PisoRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}