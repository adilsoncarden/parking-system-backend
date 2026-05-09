package com.condosaas.api.condominio;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.condosaas.api.condominio.dto.CondominioRequest;
import com.condosaas.api.condominio.dto.CondominioResponse;

@RestController
@RequestMapping("/admin/condominios")
public class CondominioController {

    private final CondominioService condominioService;

    public CondominioController(CondominioService condominioService) {
        this.condominioService = condominioService;
    }

    @GetMapping
    public ResponseEntity<List<CondominioResponse>> listarTodos() {
        return ResponseEntity.ok(condominioService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CondominioResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(condominioService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<CondominioResponse> crear(@RequestBody CondominioRequest request) {
        CondominioResponse creado = condominioService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CondominioResponse> actualizar(
            @PathVariable Long id,
            @RequestBody CondominioRequest request) {
        return ResponseEntity.ok(condominioService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        condominioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}