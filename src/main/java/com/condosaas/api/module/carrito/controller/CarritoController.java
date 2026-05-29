package com.condosaas.api.module.carrito.controller;

import com.condosaas.api.module.carrito.dto.*;
import com.condosaas.api.module.carrito.service.CarritoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin/carritos")
public class CarritoController {
    private final CarritoService service;

    public CarritoController(CarritoService service) {
        this.service = service;
    }

    @GetMapping("/prestamos")
    public ResponseEntity<List<PrestamoResponse>> findAllPrestamos() {
        return ResponseEntity.ok(service.findAllPrestamos());
    }

    @PostMapping("/prestamos")
    public ResponseEntity<PrestamoResponse> prestar(@Valid @RequestBody PrestamoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.prestar(request));
    }

    @PatchMapping("/prestamos/{id}/devolver")
    public ResponseEntity<PrestamoResponse> devolver(@PathVariable Long id) {
        return ResponseEntity.ok(service.devolver(id));
    }

    @GetMapping
    public ResponseEntity<List<CarritoResponse>> findAll(
            @RequestParam(required = false) Long entradaSalidaId,
            @RequestParam(required = false) Long condominioId) {
        return ResponseEntity.ok(service.findAll(entradaSalidaId, condominioId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarritoResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<CarritoResponse> create(@Valid @RequestBody CarritoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarritoResponse> update(@PathVariable Long id, @Valid @RequestBody CarritoRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
