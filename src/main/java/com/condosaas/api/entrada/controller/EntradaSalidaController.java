package com.condosaas.api.entrada.controller;

import com.condosaas.api.entrada.dto.EntradaSalidaRequest;
import com.condosaas.api.entrada.dto.EntradaSalidaResponse;
import com.condosaas.api.entrada.service.EntradaSalidaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/entradas")
@RequiredArgsConstructor
public class EntradaSalidaController {

    private final EntradaSalidaService entradaSalidaService;

    @GetMapping
    public ResponseEntity<List<EntradaSalidaResponse>> listarTodos() {
        return ResponseEntity.ok(entradaSalidaService.listarTodos());
    }

    @GetMapping("/condominio/{idCondominio}")
    public ResponseEntity<List<EntradaSalidaResponse>> listarPorCondominio(@PathVariable Long idCondominio) {
        return ResponseEntity.ok(entradaSalidaService.listarPorCondominio(idCondominio));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntradaSalidaResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(entradaSalidaService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<EntradaSalidaResponse> crear(@RequestBody EntradaSalidaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(entradaSalidaService.crear(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntradaSalidaResponse> actualizar(
            @PathVariable Long id,
            @RequestBody EntradaSalidaRequest request) {
        return ResponseEntity.ok(entradaSalidaService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        entradaSalidaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}