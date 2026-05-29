package com.condosaas.api.module.parkcontrol.pase_invitado.controller;

import com.condosaas.api.module.parkcontrol.pase_invitado.dto.PaseInvitadoRequest;
import com.condosaas.api.module.parkcontrol.pase_invitado.dto.PaseInvitadoResponse;
import com.condosaas.api.module.parkcontrol.pase_invitado.service.PaseInvitadoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin/parkcontrol/pases-invitado")
public class PaseInvitadoController {
    private final PaseInvitadoService service;

    public PaseInvitadoController(PaseInvitadoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PaseInvitadoResponse>> findAll(
            @RequestParam(required = false) Long condominioId,
            @RequestParam(required = false) Boolean activos) {
        return ResponseEntity.ok(service.findAll(condominioId, activos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaseInvitadoResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<PaseInvitadoResponse> create(@Valid @RequestBody PaseInvitadoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaseInvitadoResponse> update(@PathVariable Long id,
            @Valid @RequestBody PaseInvitadoRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @PatchMapping("/{id}/activar")
    public ResponseEntity<PaseInvitadoResponse> activar(@PathVariable Long id) {
        return ResponseEntity.ok(service.activar(id));
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<PaseInvitadoResponse> desactivar(@PathVariable Long id) {
        return ResponseEntity.ok(service.desactivar(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
