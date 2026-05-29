package com.condosaas.api.module.parkcontrol.zona.controller;

import com.condosaas.api.module.parkcontrol.zona.dto.ZonaRequest;
import com.condosaas.api.module.parkcontrol.zona.dto.ZonaResponse;
import com.condosaas.api.module.parkcontrol.zona.service.ZonaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin/parkcontrol/zonas")
public class ZonaController {
    private final ZonaService service;

    public ZonaController(ZonaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ZonaResponse>> findAll(@RequestParam(required = false) Long condominioId) {
        return ResponseEntity.ok(service.findAll(condominioId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ZonaResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<ZonaResponse> create(@Valid @RequestBody ZonaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ZonaResponse> update(@PathVariable Long id, @Valid @RequestBody ZonaRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
