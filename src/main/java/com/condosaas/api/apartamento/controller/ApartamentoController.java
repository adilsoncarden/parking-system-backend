package com.condosaas.api.apartamento.controller;

import com.condosaas.api.apartamento.dto.ApartamentoRequest;
import com.condosaas.api.apartamento.dto.ApartamentoResponse;
import com.condosaas.api.apartamento.service.ApartamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/apartamentos")
@RequiredArgsConstructor
public class ApartamentoController {

    private final ApartamentoService apartamentoService;

    @GetMapping
    public ResponseEntity<List<ApartamentoResponse>> listarTodos() {
        return ResponseEntity.ok(apartamentoService.listarTodos());
    }

    @GetMapping("/piso/{idPiso}")
    public ResponseEntity<List<ApartamentoResponse>> listarPorPiso(@PathVariable Long idPiso) {
        return ResponseEntity.ok(apartamentoService.listarPorPiso(idPiso));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApartamentoResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(apartamentoService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<ApartamentoResponse> crear(@RequestBody ApartamentoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(apartamentoService.crear(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApartamentoResponse> actualizar(
            @PathVariable Long id,
            @RequestBody ApartamentoRequest request) {
        return ResponseEntity.ok(apartamentoService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        apartamentoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}