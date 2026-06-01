# CondoSaaS + ParkControl — Backend API

API REST para la gestión de condominios: infraestructura residencial, usuarios con control de acceso por roles, módulo vehicular (ParkControl) y operación de carritos de carga. Pensado para consumirse desde un cliente web (React) u otras integraciones HTTP.

---

## Descripción General

El sistema centraliza información que en muchos condominios se maneja de forma manual o fragmentada: torres, pisos, apartamentos, residentes, vehículos, estacionamientos y préstamos de carritos.

**CondoSaaS (core)** cubre la estructura del edificio, usuarios, roles, permisos y carritos. **ParkControl** extiende el mismo backend con tablas y módulos para zonas de estacionamiento, plazas, accesos vehiculares, pases de invitados y permanencias, sin alterar el modelo base de estacionamiento.

Toda la lógica de negocio relevante vive en el servidor; el frontend solo consume endpoints y presenta datos.

---

## Arquitectura del Sistema

### Tipo de arquitectura

Monolito **modular**: una aplicación Spring Boot desplegable como un solo artefacto, organizada por dominios (`condominio`, `usuario`, `vehiculo`, `estacionamiento`, etc.). Cada módulo agrupa su propio controller, service, repository y DTOs.

### Capas

| Capa | Responsabilidad |
|------|-----------------|
| **Controller** | Endpoints HTTP, entrada/salida JSON |
| **Service** | Reglas de negocio y validaciones |
| **Repository** | Persistencia con Spring Data JPA |
| **Model** | Entidades mapeadas a tablas |
| **DTO** | Objetos de request/response desacoplados de la entidad |

Configuración transversal en `config/` (seguridad, JWT, filtros) y `security/` (catálogo de permisos, matcher de rutas).

### Flujo de una petición

1. El cliente envía `Authorization: Bearer <token>` (excepto login).
2. `JwtAuthFilter` valida el JWT y carga el usuario en el `SecurityContext`.
3. `PermissionAuthorizationFilter` verifica el permiso requerido para la ruta (salvo rol ADMIN).
4. El **Controller** delega al **Service**.
5. El **Service** usa **Repository** para leer/escribir en PostgreSQL.
6. Se devuelve la respuesta en JSON.

Sesión **stateless**: no hay estado de login en memoria del servidor entre peticiones.

---

## Modelo de Datos

### Organización

Base de datos relacional **PostgreSQL**, ~20 tablas agrupadas en dos bloques lógicos:

**Core (CondoSaaS)**

- Jerarquía: `condominio` → `torre` → `piso` → `apartamento`
- Seguridad: `rol`, `permiso`, `rol_permiso`, `usuario`
- Operación: `carrito_carga`, `log_prestamo_carrito`

**Extensión (ParkControl)**

- `vehiculo` (vinculado a `usuario`)
- `zona_estacionamiento` → `estacionamiento` → `detalle_plaza_parkcontrol` (1:1 con estacionamiento)
- `pase_invitado`, `log_acceso_vehicular`, `permanencia_activa`

### Relaciones principales

- Un usuario tiene un **rol** y opcionalmente un **apartamento**.
- Un vehículo pertenece a un **usuario**.
- Cada acceso vehicular se registra en **log_acceso_vehicular** (entrada/salida, método, fecha).
- Las plazas de estacionamiento pertenecen a una **zona** del condominio; ParkControl añade metadatos en `detalle_plaza_parkcontrol` sin duplicar la entidad base.

### Normalización

Entidades separadas por concepto, claves foráneas explícitas y tabla puente `rol_permiso` para la relación muchos-a-muchos entre roles y permisos. Los estados se modelan con enums en Java (`ACTIVO`, `OCUPADO`, `ENTRADA`, etc.).

Al arrancar, puede ejecutarse `schema-penalizacion.sql` para columnas de penalización en préstamos de carritos (idempotente).

---

## Seguridad

### RBAC (Role-Based Access Control)

- **Rol**: conjunto lógico (`ADMIN`, operador, etc.).
- **Permiso**: acción atómica (`VER_CARRITOS`, `EDITAR_USUARIOS`, …).
- **rol_permiso**: asignación de permisos a cada rol.
- **usuario**: cada usuario tiene un único `id_rol`.

Los permisos efectivos del usuario son los de su rol. Se gestionan con `GET/PUT /api/roles/{id}/permisos`.

### Aplicación en el backend

1. **Login** (`POST /api/auth/login`): valida credenciales con **BCrypt** y genera JWT.
2. El token incluye email, nombre del rol y lista de permisos.
3. En cada request, los permisos se cargan como `GrantedAuthority` en Spring Security.
4. `ApiPermissionMatcher` asocia método + ruta a un permiso concreto.
5. El rol **ADMIN** omite la verificación permiso a permiso.

Respuestas habituales: `401` (no autenticado / token inválido), `403` (sin permiso).

### JWT

- Librería: JJWT 0.11.5
- Configuración: `jwt.secret`, `jwt.expiration` en `application.properties`
- Filtro: `JwtAuthFilter` (antes de `UsernamePasswordAuthenticationFilter`)

---

## Funcionalidades Principales

| Módulo | Descripción |
|--------|-------------|
| **Infraestructura** | CRUD de condominios, torres, pisos y apartamentos |
| **Usuarios y roles** | Alta de usuarios, asignación de rol y apartamento; gestión de roles y permisos |
| **Vehículos** | Registro por placa, asociado al usuario |
| **Control de accesos** | Logs de entrada/salida, pases de invitado, permanencias activas |
| **Estacionamientos** | Zonas, plazas con estado de ocupación; extensión ParkControl por plaza |
| **Carritos** | Inventario por condominio; préstamos con tiempo límite, penalización y pago |
| **Dashboard** | Métricas agregadas de apartamentos y carritos |

Patrón de rutas CRUD habitual: `POST .../create`, `GET ...`, `GET .../{id}`, `PUT .../{id}/update`, `DELETE .../{id}/delete`.

Documentación detallada de endpoints: ver `ENDPOINTS_API.md` en la raíz del repositorio padre.

---

## Tecnologías Utilizadas

- Java 17
- Spring Boot 4
- Spring Web MVC
- Spring Security
- Spring Data JPA / Hibernate
- PostgreSQL
- JWT (JJWT)
- Lombok
- Bean Validation
- Maven
- SpringDoc OpenAPI (opcional en runtime)

---

## Estructura del Proyecto

```
parking-system-backend/
├── src/main/java/com/condosaas/api/
│   ├── ApiApplication.java          # Punto de entrada
│   ├── config/                      # Security, JWT, filtros, inicialización
│   ├── exception/                   # Manejo global de errores
│   ├── security/                    # Catálogo y matcher de permisos
│   └── module/
│       ├── auth/                    # Login
│       ├── condominio/ torre/ piso/ apartamento/
│       ├── usuario/ rol/ permiso/
│       ├── vehiculo/ zona_estacionamiento/ estacionamiento/
│       ├── detalle_plaza_parkcontrol/
│       ├── log_acceso_vehicular/ pase_invitado/ permanencia_activa/
│       ├── carrito_carga/ log_prestamo_carrito/
│       └── dashboard/
│           ├── controller/
│           ├── service/ (+ impl)
│           ├── repository/
│           ├── model/
│           └── dto/
├── src/main/resources/
│   ├── application.properties       # No versionado (ver instalación)
│   └── schema-penalizacion.sql      # Migración idempotente de columnas
└── pom.xml
```

Cada módulo bajo `module/` sigue la misma convención de paquetes.

---

## Instalación y Ejecución

### Requisitos

- JDK 17+
- Maven 3.8+
- PostgreSQL 14+ (local o remoto, p. ej. Neon)

### Configuración

El archivo `src/main/resources/application.properties` no está en el repositorio. Crear uno con al menos:

```properties
spring.application.name=api

spring.datasource.url=jdbc:postgresql://localhost:5432/condosaas
spring.datasource.username=postgres
spring.datasource.password=tu_password
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect

jwt.secret=clave_secreta_de_al_menos_32_caracteres_para_hs256
jwt.expiration=86400000

cors.allowed-origins=http://localhost:5173
```

Alternativa: variables de entorno `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`, `CORS_ALLOWED_ORIGINS`.

### Ejecutar

```bash
cd parking-system-backend
mvn clean install
mvn spring-boot:run
```

La API queda disponible en `http://localhost:8080`.

### Probar autenticación

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@condosaas.com","password":"admin123"}'
```

Usar el `token` devuelto en el header `Authorization: Bearer <token>` para el resto de peticiones.

Al primer arranque, `PermissionDataInitializer` puede crear permisos base y rol ADMIN si la base está vacía (según datos semilla del proyecto).

---

## Buenas Prácticas Aplicadas

- Separación por capas y por dominio (bounded contexts ligeros dentro del monolito).
- DTOs para requests/responses; entidades JPA no expuestas directamente.
- Reglas de negocio concentradas en services (validación de penalizaciones, estados de carrito, etc.).
- Seguridad en dos niveles: autenticación JWT + autorización por permiso.
- Consultas con `JOIN FETCH` en listados con relaciones para reducir N+1.
- `GlobalExceptionHandler` para errores de negocio (`400`) y no encontrado (`404`).
- API stateless y CORS configurado para frontend en desarrollo.

---

## Consideraciones

- Los cambios de permisos en un rol no se reflejan hasta que el usuario **vuelva a iniciar sesión** (permisos embebidos en el JWT).
- `spring.jpa.hibernate.ddl-auto=update` es conveniente en desarrollo; en producción conviene migraciones controladas (Flyway/Liquibase).
- Módulos vehiculares/estacionamiento requieren autenticación JWT; no todos tienen permiso granular en `PermisoCatalog` (solo endpoints de infraestructura, carritos y configuración están mapeados).
- El monolito concentra todo el tráfico; si ParkControl crece mucho en carga, podría evaluarse extraerlo como servicio independiente.
- `application.properties` y `.env` están ignorados por Git; no subir secretos al repositorio.

---

## Autor

Proyecto académico / desarrollo — **Equipo CondoSaaS**.

Para dudas sobre endpoints o despliegue con frontend, revisar también la documentación en el repositorio raíz (`parking-system`).
