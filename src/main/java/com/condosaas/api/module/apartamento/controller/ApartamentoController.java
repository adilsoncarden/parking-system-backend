package com.condosaas.api.module.apartamento.controller;

import com.condosaas.api.module.apartamento.dto.ApartamentoRequest;
import com.condosaas.api.module.apartamento.dto.ApartamentoResponse;
import com.condosaas.api.module.apartamento.service.ApartamentoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin/apartamentos")
public class ApartamentoController {
    private final ApartamentoService service;

    public ApartamentoController(ApartamentoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ApartamentoResponse>> findAll(@RequestParam(required = false) Long pisoId) {
        return ResponseEntity.ok(service.findAll(pisoId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApartamentoResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<ApartamentoResponse> create(@Valid @RequestBody ApartamentoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApartamentoResponse> update(@PathVariable Long id,
            @Valid @RequestBody ApartamentoRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
