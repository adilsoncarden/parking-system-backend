package com.condosaas.api.module.parkcontrol.plaza.controller;

import com.condosaas.api.module.enums.EstadoOcupacion;
import com.condosaas.api.module.parkcontrol.plaza.dto.PlazaRequest;
import com.condosaas.api.module.parkcontrol.plaza.dto.PlazaResponse;
import com.condosaas.api.module.parkcontrol.plaza.service.PlazaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin/parkcontrol/plazas")
public class PlazaController {
    private final PlazaService service;

    public PlazaController(PlazaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PlazaResponse>> findAll(
            @RequestParam(required = false) Long idZona,
            @RequestParam(required = false) Long condominioId) {
        return ResponseEntity.ok(service.findAll(idZona, condominioId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlazaResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<PlazaResponse> create(@Valid @RequestBody PlazaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlazaResponse> update(@PathVariable Long id, @Valid @RequestBody PlazaRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<PlazaResponse> cambiarEstado(@PathVariable Long id, @RequestParam EstadoOcupacion estado) {
        return ResponseEntity.ok(service.cambiarEstado(id, estado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
