package com.condosaas.api.module.log_prestamo_carrito.controller;

import com.condosaas.api.module.log_prestamo_carrito.dto.*;
import com.condosaas.api.module.log_prestamo_carrito.service.LogPrestamoCarritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prestamos-carrito")
@RequiredArgsConstructor
public class LogPrestamoCarritoController {

    private final LogPrestamoCarritoService service;

    @PostMapping("/create")
    public ResponseEntity<LogPrestamoCarritoResponseDTO> create(@RequestBody LogPrestamoCarritoRequestDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("")
    public ResponseEntity<List<LogPrestamoCarritoResponseDTO>> getAll(
            @RequestParam(required = false) Long carritoId,
            @RequestParam(required = false) Long usuarioId) {
        return ResponseEntity.ok(service.getAll(carritoId, usuarioId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LogPrestamoCarritoResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<LogPrestamoCarritoResponseDTO> update(@PathVariable Long id,
            @RequestBody LogPrestamoCarritoRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @PostMapping("/{id}/devolver")
    public ResponseEntity<LogPrestamoCarritoResponseDTO> devolver(@PathVariable Long id,
            @RequestParam(required = false) Long entradaDevolucionId) {
        return ResponseEntity.ok(service.registrarDevolucion(id, entradaDevolucionId));
    }

    @PatchMapping("/{id}/pagar")
    public ResponseEntity<LogPrestamoCarritoResponseDTO> pagar(@PathVariable Long id) {
        return ResponseEntity.ok(service.marcarPagado(id));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
