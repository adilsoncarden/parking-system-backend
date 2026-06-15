package com.condosaas.api.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.regex.Pattern;

@Component
public class ApiPermissionMatcher {

    private record Rule(Pattern pattern, HttpMethod method, String permission) {
    }

    private static final Rule[] RULES = {
            rule("GET", "/api/dashboard/parking", PermisoCatalog.VER_PARKING),
            rule("GET", "/api/dashboard(/.*)?", PermisoCatalog.VER_DASHBOARD),

            rule("GET", "/api/condominios(/.*)?", PermisoCatalog.VER_CONDOMINIOS),
            rule("POST", "/api/condominios/create", PermisoCatalog.CREAR_CONDOMINIOS),
            rule("PUT", "/api/condominios/\\d+/update", PermisoCatalog.EDITAR_CONDOMINIOS),
            rule("DELETE", "/api/condominios/\\d+/delete", PermisoCatalog.ELIMINAR_CONDOMINIOS),

            rule("GET", "/api/torres(/.*)?", PermisoCatalog.VER_TORRES),
            rule("POST", "/api/torres/create", PermisoCatalog.CREAR_TORRES),
            rule("PUT", "/api/torres/\\d+/update", PermisoCatalog.EDITAR_TORRES),
            rule("DELETE", "/api/torres/\\d+/delete", PermisoCatalog.ELIMINAR_TORRES),

            rule("GET", "/api/pisos(/.*)?", PermisoCatalog.VER_PISOS),
            rule("POST", "/api/pisos/create", PermisoCatalog.CREAR_PISOS),
            rule("PUT", "/api/pisos/\\d+/update", PermisoCatalog.EDITAR_PISOS),
            rule("DELETE", "/api/pisos/\\d+/delete", PermisoCatalog.ELIMINAR_PISOS),

            rule("GET", "/api/apartamentos(/.*)?", PermisoCatalog.VER_APARTAMENTOS),
            rule("POST", "/api/apartamentos/create", PermisoCatalog.CREAR_APARTAMENTOS),
            rule("PUT", "/api/apartamentos/\\d+/update", PermisoCatalog.EDITAR_APARTAMENTOS),
            rule("DELETE", "/api/apartamentos/\\d+/delete", PermisoCatalog.ELIMINAR_APARTAMENTOS),

            rule("GET", "/api/entradas(/.*)?", PermisoCatalog.VER_ENTRADAS),
            rule("POST", "/api/entradas/create", PermisoCatalog.CREAR_ENTRADAS),
            rule("PUT", "/api/entradas/\\d+/update", PermisoCatalog.EDITAR_ENTRADAS),
            rule("DELETE", "/api/entradas/\\d+/delete", PermisoCatalog.ELIMINAR_ENTRADAS),

            rule("GET", "/api/carritos(/.*)?", PermisoCatalog.VER_CARRITOS),
            rule("POST", "/api/carritos/create", PermisoCatalog.CREAR_CARRITOS),
            rule("PUT", "/api/carritos/\\d+/update", PermisoCatalog.EDITAR_CARRITOS),
            rule("DELETE", "/api/carritos/\\d+/delete", PermisoCatalog.ELIMINAR_CARRITOS),

            rule("GET", "/api/prestamos-carrito(/.*)?", PermisoCatalog.VER_PRESTAMOS),
            rule("POST", "/api/prestamos-carrito/create", PermisoCatalog.CREAR_PRESTAMOS),
            rule("POST", "/api/prestamos-carrito/\\d+/devolver", PermisoCatalog.EDITAR_PRESTAMOS),
            rule("PATCH", "/api/prestamos-carrito/\\d+/pagar", PermisoCatalog.EDITAR_PRESTAMOS),
            rule("PUT", "/api/prestamos-carrito/\\d+/update", PermisoCatalog.EDITAR_PRESTAMOS),
            rule("DELETE", "/api/prestamos-carrito/\\d+/delete", PermisoCatalog.ELIMINAR_PRESTAMOS),

            rule("GET", "/api/usuarios(/.*)?", PermisoCatalog.VER_CONFIGURACION),
            rule("POST", "/api/usuarios/create", PermisoCatalog.CREAR_CONFIGURACION),
            rule("PUT", "/api/usuarios/\\d+/update", PermisoCatalog.EDITAR_CONFIGURACION),
            rule("DELETE", "/api/usuarios/\\d+/delete", PermisoCatalog.ELIMINAR_CONFIGURACION),

            rule("GET", "/api/roles(/.*)?", PermisoCatalog.VER_CONFIGURACION),
            rule("POST", "/api/roles/create", PermisoCatalog.GESTIONAR_PERMISOS),
            rule("PUT", "/api/roles/\\d+/update", PermisoCatalog.GESTIONAR_PERMISOS),
            rule("DELETE", "/api/roles/\\d+/delete", PermisoCatalog.GESTIONAR_PERMISOS),
            rule("GET", "/api/roles/\\d+/permisos", PermisoCatalog.GESTIONAR_PERMISOS),
            rule("PUT", "/api/roles/\\d+/permisos", PermisoCatalog.GESTIONAR_PERMISOS),

            rule("GET", "/api/permisos(/.*)?", PermisoCatalog.GESTIONAR_PERMISOS),
            rule("POST", "/api/permisos/create", PermisoCatalog.GESTIONAR_PERMISOS),
            rule("PUT", "/api/permisos/\\d+/update", PermisoCatalog.GESTIONAR_PERMISOS),
            rule("DELETE", "/api/permisos/\\d+/delete", PermisoCatalog.GESTIONAR_PERMISOS),

            // ── Parking (ParkControl) ─────────────────────────────────────
            rule("GET", "/api/vehiculos(/.*)?", PermisoCatalog.VER_VEHICULOS),
            rule("POST", "/api/vehiculos/create", PermisoCatalog.CREAR_VEHICULOS),
            rule("PUT", "/api/vehiculos/\\d+/update", PermisoCatalog.EDITAR_VEHICULOS),
            rule("DELETE", "/api/vehiculos/\\d+/delete", PermisoCatalog.ELIMINAR_VEHICULOS),

            rule("GET", "/api/estacionamiento(/.*)?", PermisoCatalog.VER_ESTACIONAMIENTOS),
            rule("POST", "/api/estacionamiento/create", PermisoCatalog.CREAR_ESTACIONAMIENTOS),
            rule("PUT", "/api/estacionamiento/\\d+/update", PermisoCatalog.EDITAR_ESTACIONAMIENTOS),
            rule("DELETE", "/api/estacionamiento/\\d+/delete", PermisoCatalog.ELIMINAR_ESTACIONAMIENTOS),

            rule("GET", "/api/zonas-estacionamiento(/.*)?", PermisoCatalog.VER_ZONAS),
            rule("POST", "/api/zonas-estacionamiento/create", PermisoCatalog.CREAR_ZONAS),
            rule("PUT", "/api/zonas-estacionamiento/\\d+/update", PermisoCatalog.EDITAR_ZONAS),
            rule("DELETE", "/api/zonas-estacionamiento/\\d+/delete", PermisoCatalog.ELIMINAR_ZONAS),

            rule("GET", "/api/detalles-plaza(/.*)?", PermisoCatalog.VER_PLAZAS),
            rule("POST", "/api/detalles-plaza/create", PermisoCatalog.CREAR_PLAZAS),
            rule("PUT", "/api/detalles-plaza/\\d+/update", PermisoCatalog.EDITAR_PLAZAS),
            rule("DELETE", "/api/detalles-plaza/\\d+/delete", PermisoCatalog.ELIMINAR_PLAZAS),

            rule("GET", "/api/pases-invitado(/.*)?", PermisoCatalog.VER_PASES),
            rule("POST", "/api/pases-invitado/create", PermisoCatalog.CREAR_PASES),
            rule("PUT", "/api/pases-invitado/\\d+/update", PermisoCatalog.EDITAR_PASES),
            rule("DELETE", "/api/pases-invitado/\\d+/delete", PermisoCatalog.ELIMINAR_PASES),

            rule("POST", "/api/permanencias/registrar-entrada", PermisoCatalog.CREAR_ACCESOS),
            rule("POST", "/api/permanencias/registrar-salida", PermisoCatalog.EDITAR_ACCESOS),
            rule("GET", "/api/permanencias(/.*)?", PermisoCatalog.VER_ACCESOS),
            rule("POST", "/api/permanencias/create", PermisoCatalog.CREAR_ACCESOS),
            rule("PUT", "/api/permanencias/\\d+/update", PermisoCatalog.EDITAR_ACCESOS),
            rule("DELETE", "/api/permanencias/\\d+/delete", PermisoCatalog.ELIMINAR_ACCESOS),

            rule("GET", "/api/logs-acceso-vehicular(/.*)?", PermisoCatalog.VER_ACCESOS),
            rule("POST", "/api/logs-acceso-vehicular/create", PermisoCatalog.CREAR_ACCESOS),
            rule("DELETE", "/api/logs-acceso-vehicular/\\d+/delete", PermisoCatalog.ELIMINAR_ACCESOS),

            // Utilidad de poblamiento (solo admin / gestores)
            rule("POST", "/api/seed/.*", PermisoCatalog.GESTIONAR_PERMISOS),
    };

    private static Rule rule(String method, String regex, String permission) {
        return new Rule(Pattern.compile("^" + regex + "$"), HttpMethod.valueOf(method), permission);
    }

    public Optional<String> resolve(HttpServletRequest request) {
        String path = ApiRequestPaths.resolve(request);
        HttpMethod method = HttpMethod.valueOf(request.getMethod());

        for (Rule rule : RULES) {
            if (rule.method == method && rule.pattern.matcher(path).matches()) {
                return Optional.of(rule.permission);
            }
        }
        return Optional.empty();
    }
}
