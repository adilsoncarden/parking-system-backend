package com.condosaas.api.module.piso.controller;

import com.condosaas.api.module.piso.dto.PisoRequest;
import com.condosaas.api.module.piso.dto.PisoResponse;
import com.condosaas.api.module.piso.service.PisoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin/pisos")
public class PisoController {
    private final PisoService service;

    public PisoController(PisoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PisoResponse>> findAll(@RequestParam(required = false) Long torreId) {
        return ResponseEntity.ok(service.findAll(torreId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PisoResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<PisoResponse> create(@Valid @RequestBody PisoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PisoResponse> update(@PathVariable Long id, @Valid @RequestBody PisoRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
