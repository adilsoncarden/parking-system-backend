package com.condosaas.api.module.parkcontrol.permanencia.controller;

import com.condosaas.api.module.parkcontrol.permanencia.dto.PermanenciaRequest;
import com.condosaas.api.module.parkcontrol.permanencia.dto.PermanenciaResponse;
import com.condosaas.api.module.parkcontrol.permanencia.service.PermanenciaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin/parkcontrol/permanencias")
public class PermanenciaController {
    private final PermanenciaService service;

    public PermanenciaController(PermanenciaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PermanenciaResponse>> findAll(@RequestParam(required = false) Long condominioId) {
        return ResponseEntity.ok(service.findAll(condominioId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PermanenciaResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/calcular")
    public ResponseEntity<PermanenciaResponse> calcular(@Valid @RequestBody PermanenciaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.calcular(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
