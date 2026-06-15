# parking-system-backend

API Central de CondoSaaS (gestión de condominios, carritos de carga y estacionamiento).

## Cómo correr

Requiere las variables de entorno de la base de datos (Neon) en `.env`:

```
SPRING_DATASOURCE_URL=...
SPRING_DATASOURCE_USERNAME=...
SPRING_DATASOURCE_PASSWORD=...
CORS_ALLOWED_ORIGINS=http://localhost:5173   # opcional, default ya incluido
```

Arranque: `mvn spring-boot:run` (puerto 8080).

## Reglas de negocio configurables / a tener en cuenta

### Entradas (puertas) por condominio
- La cantidad de entradas por condominio **NO está hardcodeada**: es configurable.
- Propiedad: **`app.condominio.max-entradas`** en `application.properties` (**por defecto `2`**).
  - Clase de configuración: `config/CondominioProperties.java`.
  - Validación: `module/entrada/service/impl/EntradaServiceImpl.java` (al crear/mover una entrada).
- Cada `Entrada` tiene `capacidadCarritos` (cuántos carritos caben en el puesto de esa puerta).

### Carritos de carga
- Cada carrito queda **fijo a una entrada** (campo `entrada` en `CarritoCarga`). No se cambia de entrada.
- Al crear un carrito se valida que no se exceda la `capacidadCarritos` de esa entrada
  (ej.: 5 carritos en una puerta + 5 en otra = 10). Ver `module/carrito_carga/service/impl/CarritoCargaServiceImpl.java`.
- **Préstamo / devolución**: el carrito se **devuelve por la MISMA entrada por la que salió**.
  Enforzado en `module/log_prestamo_carrito/service/impl/LogPrestamoCarritoServiceImpl.java`.
- Penalización por exceso de tiempo: `carrito.penalizacion.tiempo-limite-minutos` y
  `carrito.penalizacion.tarifa-por-minuto` (clase `config/CarritoPenalizacionProperties.java`).

### Vehículos (estacionamiento)
- Los autos **no** tienen restricción de puerta: pueden entrar por una y salir por otra
  (a diferencia de los carritos). El control de acceso registra la puerta usada.

## Roles y permisos
- Sistema dinámico: `Rol` + `Permiso` + `RolPermiso` (asignación configurable por rol).
- Endpoints: `GET/POST /api/roles/{id}/permisos`, CRUD en `/api/roles` y `/api/permisos`.
- El rol **ADMIN** del sistema (superadmin) hace bypass de los chequeos de permiso.
