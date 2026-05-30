package com.condosaas.api.module.log_acceso_vehicular.controller;

import com.condosaas.api.module.log_acceso_vehicular.dto.*;
import com.condosaas.api.module.log_acceso_vehicular.service.LogAccesoVehicularService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logs-acceso-vehicular")
@RequiredArgsConstructor
public class LogAccesoVehicularController {

    private final LogAccesoVehicularService service;

    @PostMapping("/create")
    public ResponseEntity<LogAccesoVehicularResponseDTO> create(@RequestBody LogAccesoVehicularRequestDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("")
    public ResponseEntity<List<LogAccesoVehicularResponseDTO>> getAll(
            @RequestParam(required = false) Long vehiculoId,
            @RequestParam(required = false) Long paseInvitadoId) {
        return ResponseEntity.ok(service.getAll(vehiculoId, paseInvitadoId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LogAccesoVehicularResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}