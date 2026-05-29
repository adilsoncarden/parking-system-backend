package com.condosaas.api.module.torre.controller;

import com.condosaas.api.module.torre.dto.*;
import com.condosaas.api.module.torre.service.TorreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/torres")
@RequiredArgsConstructor
public class TorreController {

    private final TorreService service;

    @PostMapping("/create")
    public ResponseEntity<TorreResponseDTO> create(@RequestBody TorreRequestDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("")
    public ResponseEntity<List<TorreResponseDTO>> getAll(
            @RequestParam(required = false) Long condominioId) {
        return ResponseEntity.ok(service.getAll(condominioId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TorreResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<TorreResponseDTO> update(@PathVariable Long id,
            @RequestBody TorreRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}