package com.condosaas.api.module.condominio.controller;

import com.condosaas.api.module.condominio.dto.*;
import com.condosaas.api.module.condominio.service.CondominioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/condominios")
@RequiredArgsConstructor
public class CondominioController {

    private final CondominioService service;

    @PostMapping("/create")
    public ResponseEntity<CondominioResponseDTO> create(@RequestBody CondominioRequestDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("")
    public ResponseEntity<List<CondominioResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CondominioResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<CondominioResponseDTO> update(@PathVariable Long id,
            @RequestBody CondominioRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}