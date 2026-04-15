# Quickstart: CRUD de Departamentos

## Prerequisites

- Java 17 disponible.
- Maven disponible o uso del contenedor Docker definido por el proyecto.
- Docker y Docker Compose habilitados.

## 1. Levantar PostgreSQL local

```bash
docker compose up -d postgres
```

Variables relevantes:

- `DB_NAME` default: `dsw01db`
- `DB_USERNAME` default: `postgres`
- `DB_PASSWORD` default: `postgres`
- `APP_BASIC_USER` default: `admin`
- `APP_BASIC_PASSWORD` default: `admin123`

## 2. Ejecutar la aplicación

```bash
./mvnw spring-boot:run
```

Si no existe `mvnw`, usar:

```bash
mvn spring-boot:run
```

## 3. Verificar documentación y salud

```bash
curl http://localhost:8080/actuator/health
curl http://localhost:8080/v3/api-docs | jq '.info.title'
```

Swagger UI:

- `http://localhost:8080/swagger-ui.html`

## 4. Probar alta de departamento

```bash
curl -u admin:admin123 \
  -H 'Content-Type: application/json' \
  -d '{"clave":"D001","nombre":"Ventas"}' \
  http://localhost:8080/api/v1/departamentos
```

Resultado esperado:

- `201 Created`

## 5. Probar consulta por clave

```bash
curl -u admin:admin123 \
  http://localhost:8080/api/v1/departamentos/D001
```

Resultado esperado:

- `200 OK` para `D001` existente.
- `404 Not Found` para clave inexistente.

## 6. Probar listado paginado

```bash
curl -u admin:admin123 \
  'http://localhost:8080/api/v1/departamentos?page=0&size=10'
```

Resultado esperado:

- `200 OK`
- Contenido ordenado por `clave` ascendente.
- Metadatos con `page`, `size`, `totalElements`, `totalPages`.

Casos inválidos:

```bash
curl -u admin:admin123 \
  'http://localhost:8080/api/v1/departamentos?page=-1&size=10'

curl -u admin:admin123 \
  'http://localhost:8080/api/v1/departamentos?page=0&size=51'
```

Resultado esperado:

- `400 Bad Request`

## 7. Probar actualización

```bash
curl -u admin:admin123 \
  -X PUT \
  -H 'Content-Type: application/json' \
  -d '{"nombre":"Ventas Norte"}' \
  http://localhost:8080/api/v1/departamentos/D001
```

Resultado esperado:

- `200 OK` si existe.
- `404 Not Found` si no existe.

## 8. Probar eliminación

```bash
curl -u admin:admin123 \
  -X DELETE \
  http://localhost:8080/api/v1/departamentos/D001
```

Resultado esperado:

- `204 No Content` si la clave existe y se elimina.
- `404 Not Found` si la clave no existe.

## 9. Suite mínima de validación

```bash
mvn test -Dtest='*Departamento*Test'
```

Verificaciones mínimas:

- Rutas versionadas bajo `/api/v1/departamentos`.
- Reglas de autenticación HTTP Basic.
- Paginación con defaults y límite máximo.
- Orden ascendente por `clave`.
- `DELETE` inexistente devuelve `404`.
