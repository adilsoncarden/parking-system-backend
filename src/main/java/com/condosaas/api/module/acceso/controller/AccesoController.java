package com.condosaas.api.module.acceso.controller;

import com.condosaas.api.module.acceso.dto.*;
import com.condosaas.api.module.acceso.service.AccesoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin/acceso")
public class AccesoController {
    private final AccesoService service;

    public AccesoController(AccesoService service) {
        this.service = service;
    }

    @GetMapping("/entradas-salidas")
    public ResponseEntity<List<EntradaSalidaResponse>> findAllEntradasSalidas(
            @RequestParam(required = false) Long condominioId) {
        return ResponseEntity.ok(service.findAllEntradasSalidas(condominioId));
    }

    @GetMapping("/entradas-salidas/{id}")
    public ResponseEntity<EntradaSalidaResponse> findEntradaSalidaById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findEntradaSalidaById(id));
    }

    @PostMapping("/entradas-salidas")
    public ResponseEntity<EntradaSalidaResponse> createEntradaSalida(@Valid @RequestBody EntradaSalidaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createEntradaSalida(request));
    }

    @PutMapping("/entradas-salidas/{id}")
    public ResponseEntity<EntradaSalidaResponse> updateEntradaSalida(@PathVariable Long id,
            @Valid @RequestBody EntradaSalidaRequest request) {
        return ResponseEntity.ok(service.updateEntradaSalida(id, request));
    }

    @DeleteMapping("/entradas-salidas/{id}")
    public ResponseEntity<Void> deleteEntradaSalida(@PathVariable Long id) {
        service.deleteEntradaSalida(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/movimientos")
    public ResponseEntity<List<AccesoVehicularResponse>> findAllMovimientos(
            @RequestParam(required = false) Long condominioId,
            @RequestParam(required = false) String placa) {
        return ResponseEntity.ok(service.findAllMovimientos(condominioId, placa));
    }

    @GetMapping("/movimientos/{id}")
    public ResponseEntity<AccesoVehicularResponse> findMovimientoById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findMovimientoById(id));
    }

    @PostMapping("/entrada")
    public ResponseEntity<AccesoVehicularResponse> registrarEntrada(
            @Valid @RequestBody AccesoVehicularRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrarEntrada(request));
    }

    @PostMapping("/salida")
    public ResponseEntity<AccesoVehicularResponse> registrarSalida(@Valid @RequestBody AccesoVehicularRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrarSalida(request));
    }
}
