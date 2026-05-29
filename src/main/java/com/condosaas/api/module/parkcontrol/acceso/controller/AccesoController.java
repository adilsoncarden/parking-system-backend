package com.condosaas.api.module.parkcontrol.acceso.controller;

import com.condosaas.api.module.parkcontrol.acceso.dto.AccesoRequest;
import com.condosaas.api.module.parkcontrol.acceso.dto.AccesoResponse;
import com.condosaas.api.module.parkcontrol.acceso.service.AccesoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController("accesoParkControlController")
@RequestMapping("/admin/parkcontrol/accesos")
public class AccesoController {
    private final AccesoService service;

    public AccesoController(@Qualifier("accesoParkControlServiceImpl") AccesoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<AccesoResponse>> findAll(
            @RequestParam(required = false) Long condominioId,
            @RequestParam(required = false) Long idVehiculo) {
        return ResponseEntity.ok(service.findAll(condominioId, idVehiculo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccesoResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/entrada")
    public ResponseEntity<AccesoResponse> registrarEntrada(@Valid @RequestBody AccesoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrarEntrada(request));
    }

    @PatchMapping("/{id}/salida")
    public ResponseEntity<AccesoResponse> registrarSalida(@PathVariable Long id) {
        return ResponseEntity.ok(service.registrarSalida(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
