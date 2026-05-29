package com.condosaas.api.module.condominio.controller;

import com.condosaas.api.module.condominio.dto.CondominioRequest;
import com.condosaas.api.module.condominio.dto.CondominioResponse;
import com.condosaas.api.module.condominio.service.CondominioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin/condominios")
public class CondominioController {
    private final CondominioService service;

    public CondominioController(CondominioService service) {
        this.service = service;
    }

    @GetMapping("")
    public ResponseEntity<List<CondominioResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CondominioResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<CondominioResponse> create(@Valid @RequestBody CondominioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CondominioResponse> update(@PathVariable Long id,
            @Valid @RequestBody CondominioRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
