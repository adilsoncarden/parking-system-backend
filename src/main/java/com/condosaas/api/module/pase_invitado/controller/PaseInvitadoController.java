package com.condosaas.api.module.pase_invitado.controller;

import com.condosaas.api.module.pase_invitado.dto.*;
import com.condosaas.api.module.pase_invitado.service.PaseInvitadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pases-invitado")
@RequiredArgsConstructor
public class PaseInvitadoController {

    private final PaseInvitadoService service;

    @PostMapping("/create")
    public ResponseEntity<PaseInvitadoResponseDTO> create(@RequestBody PaseInvitadoRequestDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("")
    public ResponseEntity<List<PaseInvitadoResponseDTO>> getAll(
            @RequestParam(required = false) Long usuarioId) {
        return ResponseEntity.ok(service.getAll(usuarioId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaseInvitadoResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<PaseInvitadoResponseDTO> update(@PathVariable Long id,
            @RequestBody PaseInvitadoRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}