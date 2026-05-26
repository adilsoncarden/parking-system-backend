package com.condosaas.api.carrito.controller;

import com.condosaas.api.carrito.dto.CarritoCargaRequest;
import com.condosaas.api.carrito.dto.CarritoCargaResponse;
import com.condosaas.api.carrito.service.CarritoCargaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/carritos")
@RequiredArgsConstructor
public class CarritoCargaController {

    private final CarritoCargaService carritoCargaService;

    @GetMapping
    public ResponseEntity<List<CarritoCargaResponse>> listarTodos() {
        return ResponseEntity.ok(carritoCargaService.listarTodos());
    }

    @GetMapping("/entrada/{idEntradaSalida}")
    public ResponseEntity<List<CarritoCargaResponse>> listarPorEntrada(@PathVariable Long idEntradaSalida) {
        return ResponseEntity.ok(carritoCargaService.listarPorEntrada(idEntradaSalida));
    }

    @GetMapping("/condominio/{idCondominio}")
    public ResponseEntity<List<CarritoCargaResponse>> listarPorCondominio(@PathVariable Long idCondominio) {
        return ResponseEntity.ok(carritoCargaService.listarPorCondominio(idCondominio));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarritoCargaResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(carritoCargaService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<CarritoCargaResponse> crear(@RequestBody CarritoCargaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carritoCargaService.crear(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarritoCargaResponse> actualizar(
            @PathVariable Long id,
            @RequestBody CarritoCargaRequest request) {
        return ResponseEntity.ok(carritoCargaService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        carritoCargaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/disponibilidad")
    public ResponseEntity<CarritoCargaResponse> cambiarDisponibilidad(
            @PathVariable Long id,
            @RequestParam Boolean disponible) {
        return ResponseEntity.ok(carritoCargaService.cambiarDisponibilidad(id, disponible));
    }
}