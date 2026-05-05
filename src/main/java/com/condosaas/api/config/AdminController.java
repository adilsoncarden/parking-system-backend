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

}
