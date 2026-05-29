# Auth

## POST /auth/login
```json
{
  "username": "admin",
  "password": "admin123"
}
```

---

# condominio

## GET /admin/condominios

## GET /admin/condominios/{id}

## POST /admin/condominios
```json
{
  "nombre": "Torres del Sol",
  "direccion": "Av. Principal 123",
  "tipo": "RESIDENCIAL",
  "imagen": "https://cdn.example/condo.jpg",
  "latitud": -12.0464,
  "longitud": -77.0428
}
```

## PUT /admin/condominios/{id}
```json
{
  "nombre": "Torres del Sol",
  "direccion": "Av. Principal 123",
  "tipo": "RESIDENCIAL",
  "imagen": null,
  "latitud": -12.0464,
  "longitud": -77.0428
}
```

## DELETE /admin/condominios/{id}

---

# torre

## GET /admin/torres

## GET /admin/torres?condominioId=1

## GET /admin/torres/{id}

## POST /admin/torres
```json
{
  "nombre": "Torre A",
  "cantidadPisos": 10,
  "cantidadApartamentos": 80,
  "idCondominio": 1
}
```

## PUT /admin/torres/{id}
```json
{
  "nombre": "Torre A",
  "cantidadPisos": 10,
  "cantidadApartamentos": 80,
  "idCondominio": 1
}
```

## DELETE /admin/torres/{id}

---

# piso

## GET /admin/pisos

## GET /admin/pisos?torreId=1

## GET /admin/pisos/{id}

## POST /admin/pisos
```json
{
  "numeroPiso": 3,
  "idTorre": 1
}
```

## PUT /admin/pisos/{id}
```json
{
  "numeroPiso": 3,
  "idTorre": 1
}
```

## DELETE /admin/pisos/{id}

---

# apartamento

## GET /admin/apartamentos

## GET /admin/apartamentos?pisoId=1

## GET /admin/apartamentos/{id}

## POST /admin/apartamentos
```json
{
  "numeroApartamento": "301",
  "propietario": "Juan Pérez",
  "estado": "DISPONIBLE",
  "idPiso": 1
}
```

## PUT /admin/apartamentos/{id}
```json
{
  "numeroApartamento": "301",
  "propietario": "Juan Pérez",
  "estado": "OCUPADO",
  "idPiso": 1
}
```

## DELETE /admin/apartamentos/{id}

---

# usuario

## GET /admin/usuarios

## GET /admin/usuarios/{id}

## POST /admin/usuarios
```json
{
  "username": "portero1",
  "password": "Portero123!",
  "roleId": 1
}
```

## PUT /admin/usuarios/{id}
```json
{
  "username": "portero1",
  "password": "NuevaClave123!",
  "roleId": 1
}
```

## DELETE /admin/usuarios/{id}

---

# rol

## GET /admin/roles

## GET /admin/roles/{id}

## POST /admin/roles
```json
{
  "name": "ADMIN"
}
```

## PUT /admin/roles/{id}
```json
{
  "name": "OPERADOR"
}
```

## DELETE /admin/roles/{id}

---

# estacionamiento

## GET /admin/estacionamientos

## GET /admin/estacionamientos?condominioId=1

## GET /admin/estacionamientos/{id}

## POST /admin/estacionamientos
```json
{
  "codigo": "E-101",
  "estado": "LIBRE",
  "tipo": "PROPIETARIO",
  "idApartamento": 1,
  "idCondominio": 1
}
```

## PUT /admin/estacionamientos/{id}
```json
{
  "codigo": "E-101",
  "estado": "OCUPADA",
  "tipo": "VISITANTE",
  "idApartamento": null,
  "idCondominio": 1
}
```

## PATCH /admin/estacionamientos/{id}/estado?estado=OCUPADA

## DELETE /admin/estacionamientos/{id}

---

# acceso

## GET /admin/acceso/entradas-salidas

## GET /admin/acceso/entradas-salidas?condominioId=1

## GET /admin/acceso/entradas-salidas/{id}

## POST /admin/acceso/entradas-salidas
```json
{
  "nombre": "Puerta Principal",
  "idCondominio": 1
}
```

## PUT /admin/acceso/entradas-salidas/{id}
```json
{
  "nombre": "Puerta Sur",
  "idCondominio": 1
}
```

## DELETE /admin/acceso/entradas-salidas/{id}

## GET /admin/acceso/movimientos

## GET /admin/acceso/movimientos?condominioId=1

## GET /admin/acceso/movimientos?placa=ABC123

## GET /admin/acceso/movimientos/{id}

## POST /admin/acceso/entrada
```json
{
  "placa": "ABC123",
  "idCondominio": 1,
  "idPuntoAcceso": 1,
  "observaciones": "Ingreso"
}
```

## POST /admin/acceso/salida
```json
{
  "placa": "ABC123",
  "idCondominio": 1,
  "idPuntoAcceso": 1,
  "observaciones": "Salida"
}
```

---

# carrito

## GET /admin/carritos

## GET /admin/carritos?entradaSalidaId=1

## GET /admin/carritos?condominioId=1

## GET /admin/carritos/prestamos

## GET /admin/carritos/{id}

## POST /admin/carritos
```json
{
  "nombre": "Carrito 01",
  "idEntradaSalida": 1
}
```

## PUT /admin/carritos/{id}
```json
{
  "nombre": "Carrito 01",
  "idEntradaSalida": 1
}
```

## DELETE /admin/carritos/{id}

## POST /admin/carritos/prestamos
```json
{
  "idCarrito": 1,
  "idApartamento": 1,
  "idEntradaSalida": 1
}
```

## PATCH /admin/carritos/prestamos/{id}/devolver

---

# parkcontrol - vehiculo

## GET /admin/parkcontrol/vehiculos

## GET /admin/parkcontrol/vehiculos?condominioId=1

## GET /admin/parkcontrol/vehiculos/{id}

## POST /admin/parkcontrol/vehiculos
```json
{
  "placa": "XYZ789",
  "marca": "Toyota",
  "modelo": "Yaris",
  "color": "Blanco",
  "idApartamento": 1,
  "idCondominio": 1
}
```

## PUT /admin/parkcontrol/vehiculos/{id}
```json
{
  "placa": "XYZ789",
  "marca": "Toyota",
  "modelo": "Yaris",
  "color": "Blanco",
  "idApartamento": 1,
  "idCondominio": 1
}
```

## DELETE /admin/parkcontrol/vehiculos/{id}

---

# parkcontrol - pase_invitado

## GET /admin/parkcontrol/pases-invitado

## GET /admin/parkcontrol/pases-invitado?condominioId=1

## GET /admin/parkcontrol/pases-invitado?activos=true

## GET /admin/parkcontrol/pases-invitado/{id}

## POST /admin/parkcontrol/pases-invitado
```json
{
  "codigo": "PASE-2026-001",
  "nombreVisitante": "Carlos Ruiz",
  "placaVisitante": "TMP456",
  "fechaInicio": "2026-05-28T08:00:00",
  "fechaFin": "2026-05-28T20:00:00",
  "idApartamento": 1,
  "idCondominio": 1
}
```

## PUT /admin/parkcontrol/pases-invitado/{id}
```json
{
  "codigo": "PASE-2026-001",
  "nombreVisitante": "Carlos Ruiz",
  "placaVisitante": "TMP456",
  "fechaInicio": "2026-05-28T08:00:00",
  "fechaFin": "2026-05-28T22:00:00",
  "idApartamento": 1,
  "idCondominio": 1
}
```

## PATCH /admin/parkcontrol/pases-invitado/{id}/activar

## PATCH /admin/parkcontrol/pases-invitado/{id}/desactivar

## DELETE /admin/parkcontrol/pases-invitado/{id}

---

# parkcontrol - zona

## GET /admin/parkcontrol/zonas

## GET /admin/parkcontrol/zonas?condominioId=1

## GET /admin/parkcontrol/zonas/{id}

## POST /admin/parkcontrol/zonas
```json
{
  "nombre": "Sótano 1",
  "descripcion": "Zona visitantes",
  "idCondominio": 1
}
```

## PUT /admin/parkcontrol/zonas/{id}
```json
{
  "nombre": "Sótano 1",
  "descripcion": "Zona visitantes",
  "idCondominio": 1
}
```

## DELETE /admin/parkcontrol/zonas/{id}

---

# parkcontrol - plaza

## GET /admin/parkcontrol/plazas

## GET /admin/parkcontrol/plazas?idZona=1

## GET /admin/parkcontrol/plazas?condominioId=1

## GET /admin/parkcontrol/plazas/{id}

## POST /admin/parkcontrol/plazas
```json
{
  "codigo": "P-01",
  "estado": "LIBRE",
  "idZona": 1
}
```

## PUT /admin/parkcontrol/plazas/{id}
```json
{
  "codigo": "P-01",
  "estado": "LIBRE",
  "idZona": 1
}
```

## PATCH /admin/parkcontrol/plazas/{id}/estado?estado=OCUPADA

## DELETE /admin/parkcontrol/plazas/{id}

---

# parkcontrol - acceso

## GET /admin/parkcontrol/accesos

## GET /admin/parkcontrol/accesos?condominioId=1

## GET /admin/parkcontrol/accesos?idVehiculo=1

## GET /admin/parkcontrol/accesos/{id}

## POST /admin/parkcontrol/accesos/entrada
```json
{
  "idVehiculo": 1,
  "idPlaza": 1,
  "idPaseInvitado": 1
}
```

## PATCH /admin/parkcontrol/accesos/{id}/salida

## DELETE /admin/parkcontrol/accesos/{id}

---

# parkcontrol - permanencia

## GET /admin/parkcontrol/permanencias

## GET /admin/parkcontrol/permanencias?condominioId=1

## GET /admin/parkcontrol/permanencias/{id}

## POST /admin/parkcontrol/permanencias/calcular
```json
{
  "idAcceso": 1,
  "tarifaPorHora": 2.5
}
```

## DELETE /admin/parkcontrol/permanencias/{id}
