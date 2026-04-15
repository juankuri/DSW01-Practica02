# Quickstart: CRUD de Empleados

## Prerrequisitos

- Java 17
- Docker y Docker Compose
- Maven 3.9+

## 1) Levantar PostgreSQL con Docker

Ejemplo mínimo de ejecución:

```bash
docker run --name empleados-db \
  -e POSTGRES_DB=dsw01db \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -p 5432:5432 \
  -d postgres:16
```

## 2) Variables de entorno (opcional)

Si no se definen, se usarán defaults de `application.properties`.

```bash
export DB_HOST=localhost
export DB_PORT=5432
export DB_NAME=dsw01db
export DB_USERNAME=postgres
export DB_PASSWORD=postgres
export APP_BASIC_USER=admin
export APP_BASIC_PASSWORD=admin123
```

## 3) Ejecutar aplicación

```bash
mvn spring-boot:run
```

## 4) Verificar Swagger/OpenAPI

- OpenAPI JSON: `http://localhost:8080/v3/api-docs`
- Swagger UI: `http://localhost:8080/swagger-ui.html`

## 5) Probar endpoint protegido

```bash
curl -u admin:admin123 http://localhost:8080/api/empleados
```

## 6) Flujo básico CRUD

### Crear

```bash
curl -u admin:admin123 -X POST http://localhost:8080/api/empleados \
  -H "Content-Type: application/json" \
  -d '{"clave":"EMP001","nombre":"Ana","direccion":"Calle 1","telefono":"555-111"}'
```

### Obtener por clave

```bash
curl -u admin:admin123 http://localhost:8080/api/empleados/EMP001
```

### Actualizar (con `version` actual)

```bash
curl -u admin:admin123 -X PUT http://localhost:8080/api/empleados/EMP001 \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Ana Maria","direccion":"Calle 2","telefono":"555-222","version":0}'
```

### Eliminar

```bash
curl -u admin:admin123 -X DELETE http://localhost:8080/api/empleados/EMP001
```

## 7) Pruebas recomendadas

- Unit: validación de longitud fija y padding.
- Unit: validación de longitudes 1..100 sin padding artificial.
- Integration: persistencia con PostgreSQL y reglas de unicidad.
- Contract/Controller: códigos HTTP esperados y seguridad Basic Auth.
