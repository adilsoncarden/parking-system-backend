package com.condosaas.api.piso.controller;

import com.condosaas.api.piso.dto.PisoRequest;
import com.condosaas.api.piso.dto.PisoResponse;
import com.condosaas.api.piso.service.PisoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/pisos")
@RequiredArgsConstructor
public class PisoController {

    private final PisoService pisoService;

    @GetMapping
    public ResponseEntity<List<PisoResponse>> listarTodos() {
        return ResponseEntity.ok(pisoService.listarTodos());
    }

    @GetMapping("/torre/{idTorre}")
    public ResponseEntity<List<PisoResponse>> listarPorTorre(@PathVariable Long idTorre) {
        return ResponseEntity.ok(pisoService.listarPorTorre(idTorre));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PisoResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pisoService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<PisoResponse> crear(@RequestBody PisoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pisoService.crear(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PisoResponse> actualizar(
            @PathVariable Long id,
            @RequestBody PisoRequest request) {
        return ResponseEntity.ok(pisoService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        pisoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}