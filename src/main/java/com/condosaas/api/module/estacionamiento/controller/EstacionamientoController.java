package com.condosaas.api.module.estacionamiento.controller;

import com.condosaas.api.module.enums.EstadoOcupacion;
import com.condosaas.api.module.estacionamiento.dto.EstacionamientoRequest;
import com.condosaas.api.module.estacionamiento.dto.EstacionamientoResponse;
import com.condosaas.api.module.estacionamiento.service.EstacionamientoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin/estacionamientos")
public class EstacionamientoController {
    private final EstacionamientoService service;

    public EstacionamientoController(EstacionamientoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<EstacionamientoResponse>> findAll(@RequestParam(required = false) Long condominioId) {
        return ResponseEntity.ok(service.findAll(condominioId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstacionamientoResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<EstacionamientoResponse> create(@Valid @RequestBody EstacionamientoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstacionamientoResponse> update(@PathVariable Long id, @Valid @RequestBody EstacionamientoRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<EstacionamientoResponse> updateEstado(@PathVariable Long id, @RequestParam EstadoOcupacion estado) {
        return ResponseEntity.ok(service.updateEstado(id, estado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
