package com.condosaas.api.prestamo.controller;

import com.condosaas.api.prestamo.dto.PrestamoRequest;
import com.condosaas.api.prestamo.dto.PrestamoResponse;
import com.condosaas.api.prestamo.service.PrestamoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/prestamos")
@RequiredArgsConstructor
public class PrestamoController {

    private final PrestamoService prestamoService;

    @GetMapping
    public ResponseEntity<List<PrestamoResponse>> listarTodos() {
        return ResponseEntity.ok(prestamoService.listarTodos());
    }

    @GetMapping("/activos")
    public ResponseEntity<List<PrestamoResponse>> listarActivos() {
        return ResponseEntity.ok(prestamoService.listarActivos());
    }

    @GetMapping("/multados")
    public ResponseEntity<List<PrestamoResponse>> listarMultados() {
        return ResponseEntity.ok(prestamoService.listarMultados());
    }

    @GetMapping("/condominio/{idCondominio}")
    public ResponseEntity<List<PrestamoResponse>> listarPorCondominio(@PathVariable Long idCondominio) {
        return ResponseEntity.ok(prestamoService.listarPorCondominio(idCondominio));
    }

    @PostMapping
    public ResponseEntity<PrestamoResponse> crear(@RequestBody PrestamoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(prestamoService.crear(request));
    }

    @PatchMapping("/{id}/devolver")
    public ResponseEntity<PrestamoResponse> devolver(@PathVariable Long id) {
        return ResponseEntity.ok(prestamoService.devolver(id));
    }

    @PatchMapping("/{id}/multa")
    public ResponseEntity<PrestamoResponse> aplicarMulta(@PathVariable Long id) {
        return ResponseEntity.ok(prestamoService.aplicarMulta(id));
    }
}