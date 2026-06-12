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
                GESTIONAR_PERMISOS);
    }
}
