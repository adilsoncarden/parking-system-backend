# 🤝 Guía de Contribución

Esta guía explica cómo trabajar en el proyecto de forma ordenada.

---

## 🌿 Ramas

| Rama | Propósito |
|------|-----------|
| `main` | Código estable y desplegado |
| `mejoras-docu` | Mejoras de documentación y configuración |

Crea tu rama desde `main`:

```bash
git checkout main
git pull origin main
git checkout -b nombre-de-tu-rama
```

---

## ✅ Convención de commits

Usamos el estándar [Conventional Commits](https://www.conventionalcommits.org/):

| Prefijo | Cuándo usarlo |
|---------|---------------|
| `feat:` | Nueva funcionalidad |
| `fix:` | Corrección de bug |
| `docs:` | Documentación |
| `refactor:` | Refactorización sin cambio de comportamiento |
| `style:` | Formato, espacios, punto y coma |
| `test:` | Agregar o modificar tests |
| `chore:` | Tareas de mantenimiento |

Ejemplos:
```bash
git commit -m "feat: agregar endpoint de reporte mensual de accesos"
git commit -m "fix: corregir validacion de placa duplicada en VehiculoService"
git commit -m "docs: documentar endpoints de autenticacion en README"
```

---

## 🚀 Flujo de trabajo

```bash
# 1. Asegúrate de estar actualizado
git pull origin main

# 2. Crea tu rama
git checkout -b feat/nombre-funcionalidad

# 3. Haz tus cambios y commits
git add .
git commit -m "feat: descripción del cambio"

# 4. Sube tu rama
git push origin feat/nombre-funcionalidad

# 5. Abre un Pull Request hacia main
```

---

## ⚙️ Requisitos para correr el proyecto

- Java 17+
- Maven 3.8+
- PostgreSQL (o cuenta en Neon.tech)

```bash
# 1. Configurar variables de entorno
cp .env.example .env

# 2. Compilar y correr
./mvnw spring-boot:run
```

El servidor estará disponible en `http://localhost:8080`

---

## 🌐 Demo

Backend desplegado: https://parking-system-backend-0chy.onrender.com