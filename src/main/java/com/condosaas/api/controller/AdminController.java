package com.condosaas.api.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// ═══════════════════════════════════════════════════════════
// AdminController contiene SOLO los endpoints "placeholder"
// que aún no tienen tabla real (torres, pisos, apartamentos,
// carritos, config). El de condominios se movió a su propio
// CondominioController con BD real.
// ═══════════════════════════════════════════════════════════
@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/dashboard")
    public Map<String, String> dashboard() {
        return Map.of("status", "Access Granted", "module", "Dashboard");
    }

    /*@GetMapping("/torres")
    /public Map<String, String> torres() {
        return Map.of("action", "Gestión de torres del condominio", "module", "Torres");
    }*/

    @GetMapping("/pisos")
    public Map<String, String> pisos() {
        return Map.of("action", "Gestión de niveles y pisos", "module", "Pisos");
    }

    @GetMapping("/apartamentos")
    public Map<String, String> apartamentos() {
        return Map.of("action", "Control de unidades residenciales", "module", "Apartamentos");
    }

    @GetMapping("/carritos")
    public Map<String, String> carritos() {
        return Map.of("action", "Monitor de carritos de compras/servicios", "module", "Carritos");
    }

    @GetMapping("/config")
    public Map<String, String> config() {
        return Map.of("action", "Ajustes globales del SaaS", "module", "Configuración");
    }
}