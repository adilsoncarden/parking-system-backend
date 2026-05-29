package com.condosaas.api.module.parkcontrol.vehiculo.controller;

import com.condosaas.api.module.parkcontrol.vehiculo.dto.VehiculoRequest;
import com.condosaas.api.module.parkcontrol.vehiculo.dto.VehiculoResponse;
import com.condosaas.api.module.parkcontrol.vehiculo.service.VehiculoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin/parkcontrol/vehiculos")
public class VehiculoController {
    private final VehiculoService service;

    public VehiculoController(VehiculoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<VehiculoResponse>> findAll(@RequestParam(required = false) Long condominioId) {
        return ResponseEntity.ok(service.findAll(condominioId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehiculoResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<VehiculoResponse> create(@Valid @RequestBody VehiculoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehiculoResponse> update(@PathVariable Long id, @Valid @RequestBody VehiculoRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
