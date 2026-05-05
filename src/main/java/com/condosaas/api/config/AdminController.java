package com.condosaas.api.config;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/dashboard")
    public Map<String, Object> dashboard() {
        return Map.of("module", "Dashboard", "status", "Access Granted");
    }

    @GetMapping("/condominios")
    public Map<String, String> condominios() {
        return Map.of("module", "Condominios", "action", "Listing all records");
    }

    @GetMapping("/torres")
    public Map<String, String> torres() {
        return Map.of("module", "Torres", "action", "Gestión de torres del condominio");
    }

    @GetMapping("/pisos")
    public Map<String, String> pisos() {
        return Map.of("module", "Pisos", "action", "Gestión de niveles y pisos");
    }

    @GetMapping("/apartamentos")
    public Map<String, String> apartamentos() {
        return Map.of("module", "Apartamentos", "action", "Control de unidades residenciales");
    }

    @GetMapping("/carritos")
    public Map<String, String> carritos() {
        return Map.of("module", "Carritos", "action", "Monitor de carritos de compras/servicios");
    }

}
