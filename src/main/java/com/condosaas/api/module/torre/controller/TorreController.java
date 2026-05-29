package com.condosaas.api.module.torre.controller;

import com.condosaas.api.module.torre.dto.TorreRequest;
import com.condosaas.api.module.torre.dto.TorreResponse;
import com.condosaas.api.module.torre.service.TorreService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin/torres")
public class TorreController {
    private final TorreService service;

    public TorreController(TorreService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<TorreResponse>> findAll(@RequestParam(required = false) Long condominioId) {
        return ResponseEntity.ok(service.findAll(condominioId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TorreResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<TorreResponse> create(@Valid @RequestBody TorreRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TorreResponse> update(@PathVariable Long id, @Valid @RequestBody TorreRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
