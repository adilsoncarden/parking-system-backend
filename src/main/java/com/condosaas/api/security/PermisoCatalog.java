package com.condosaas.api.security;

import java.util.List;

public final class PermisoCatalog {

    public static final String ROL_ADMIN = "ADMIN";

    public static final String VER_DASHBOARD = "VER_DASHBOARD";

    public static final String VER_CONDOMINIOS = "VER_CONDOMINIOS";
    public static final String CREAR_CONDOMINIOS = "CREAR_CONDOMINIOS";
    public static final String EDITAR_CONDOMINIOS = "EDITAR_CONDOMINIOS";
    public static final String ELIMINAR_CONDOMINIOS = "ELIMINAR_CONDOMINIOS";

    public static final String VER_TORRES = "VER_TORRES";
    public static final String CREAR_TORRES = "CREAR_TORRES";
    public static final String EDITAR_TORRES = "EDITAR_TORRES";
    public static final String ELIMINAR_TORRES = "ELIMINAR_TORRES";

    public static final String VER_PISOS = "VER_PISOS";
    public static final String CREAR_PISOS = "CREAR_PISOS";
    public static final String EDITAR_PISOS = "EDITAR_PISOS";
    public static final String ELIMINAR_PISOS = "ELIMINAR_PISOS";

    public static final String VER_APARTAMENTOS = "VER_APARTAMENTOS";
    public static final String CREAR_APARTAMENTOS = "CREAR_APARTAMENTOS";
    public static final String EDITAR_APARTAMENTOS = "EDITAR_APARTAMENTOS";
    public static final String ELIMINAR_APARTAMENTOS = "ELIMINAR_APARTAMENTOS";

    public static final String VER_ENTRADAS = "VER_ENTRADAS";
    public static final String CREAR_ENTRADAS = "CREAR_ENTRADAS";
    public static final String EDITAR_ENTRADAS = "EDITAR_ENTRADAS";
    public static final String ELIMINAR_ENTRADAS = "ELIMINAR_ENTRADAS";

    public static final String VER_CARRITOS = "VER_CARRITOS";
    public static final String CREAR_CARRITOS = "CREAR_CARRITOS";
    public static final String EDITAR_CARRITOS = "EDITAR_CARRITOS";
    public static final String ELIMINAR_CARRITOS = "ELIMINAR_CARRITOS";

    public static final String VER_PRESTAMOS = "VER_PRESTAMOS";
    public static final String CREAR_PRESTAMOS = "CREAR_PRESTAMOS";
    public static final String EDITAR_PRESTAMOS = "EDITAR_PRESTAMOS";
    public static final String ELIMINAR_PRESTAMOS = "ELIMINAR_PRESTAMOS";

    public static final String VER_CONFIGURACION = "VER_CONFIGURACION";
    public static final String CREAR_CONFIGURACION = "CREAR_CONFIGURACION";
    public static final String EDITAR_CONFIGURACION = "EDITAR_CONFIGURACION";
    public static final String ELIMINAR_CONFIGURACION = "ELIMINAR_CONFIGURACION";

    public static final String GESTIONAR_PERMISOS = "GESTIONAR_PERMISOS";

    // ── Parking (ParkControl) ─────────────────────────────────────────────
    public static final String VER_PARKING = "VER_PARKING";

    public static final String VER_VEHICULOS = "VER_VEHICULOS";
    public static final String CREAR_VEHICULOS = "CREAR_VEHICULOS";
    public static final String EDITAR_VEHICULOS = "EDITAR_VEHICULOS";
    public static final String ELIMINAR_VEHICULOS = "ELIMINAR_VEHICULOS";

    public static final String VER_ESTACIONAMIENTOS = "VER_ESTACIONAMIENTOS";
    public static final String CREAR_ESTACIONAMIENTOS = "CREAR_ESTACIONAMIENTOS";
    public static final String EDITAR_ESTACIONAMIENTOS = "EDITAR_ESTACIONAMIENTOS";
    public static final String ELIMINAR_ESTACIONAMIENTOS = "ELIMINAR_ESTACIONAMIENTOS";

    public static final String VER_ZONAS = "VER_ZONAS";
    public static final String CREAR_ZONAS = "CREAR_ZONAS";
    public static final String EDITAR_ZONAS = "EDITAR_ZONAS";
    public static final String ELIMINAR_ZONAS = "ELIMINAR_ZONAS";

    public static final String VER_PLAZAS = "VER_PLAZAS";
    public static final String CREAR_PLAZAS = "CREAR_PLAZAS";
    public static final String EDITAR_PLAZAS = "EDITAR_PLAZAS";
    public static final String ELIMINAR_PLAZAS = "ELIMINAR_PLAZAS";

    public static final String VER_PASES = "VER_PASES";
    public static final String CREAR_PASES = "CREAR_PASES";
    public static final String EDITAR_PASES = "EDITAR_PASES";
    public static final String ELIMINAR_PASES = "ELIMINAR_PASES";

    public static final String VER_ACCESOS = "VER_ACCESOS";
    public static final String CREAR_ACCESOS = "CREAR_ACCESOS";
    public static final String EDITAR_ACCESOS = "EDITAR_ACCESOS";
    public static final String ELIMINAR_ACCESOS = "ELIMINAR_ACCESOS";

    private PermisoCatalog() {
    }

    public static List<String> all() {
        return List.of(
                VER_DASHBOARD,
                VER_CONDOMINIOS, CREAR_CONDOMINIOS, EDITAR_CONDOMINIOS, ELIMINAR_CONDOMINIOS,
                VER_TORRES, CREAR_TORRES, EDITAR_TORRES, ELIMINAR_TORRES,
                VER_PISOS, CREAR_PISOS, EDITAR_PISOS, ELIMINAR_PISOS,
                VER_APARTAMENTOS, CREAR_APARTAMENTOS, EDITAR_APARTAMENTOS, ELIMINAR_APARTAMENTOS,
                VER_ENTRADAS, CREAR_ENTRADAS, EDITAR_ENTRADAS, ELIMINAR_ENTRADAS,
                VER_CARRITOS, CREAR_CARRITOS, EDITAR_CARRITOS, ELIMINAR_CARRITOS,
                VER_PRESTAMOS, CREAR_PRESTAMOS, EDITAR_PRESTAMOS, ELIMINAR_PRESTAMOS,
                VER_CONFIGURACION, CREAR_CONFIGURACION, EDITAR_CONFIGURACION, ELIMINAR_CONFIGURACION,
                GESTIONAR_PERMISOS,
                VER_PARKING,
                VER_VEHICULOS, CREAR_VEHICULOS, EDITAR_VEHICULOS, ELIMINAR_VEHICULOS,
                VER_ESTACIONAMIENTOS, CREAR_ESTACIONAMIENTOS, EDITAR_ESTACIONAMIENTOS, ELIMINAR_ESTACIONAMIENTOS,
                VER_ZONAS, CREAR_ZONAS, EDITAR_ZONAS, ELIMINAR_ZONAS,
                VER_PLAZAS, CREAR_PLAZAS, EDITAR_PLAZAS, ELIMINAR_PLAZAS,
                VER_PASES, CREAR_PASES, EDITAR_PASES, ELIMINAR_PASES,
                VER_ACCESOS, CREAR_ACCESOS, EDITAR_ACCESOS, ELIMINAR_ACCESOS);
    }
}
