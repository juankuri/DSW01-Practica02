# Data Model: CRUD de Departamentos

## Entity: Departamento

- Purpose: Representa un registro administrativo del catálogo de departamentos.
- Primary Key: `clave`.
- Fields:
  - `clave`: string normalizado, obligatorio, único, longitud 1..100 tras `trim()`.
  - `nombre`: string normalizado, obligatorio, longitud 1..100 tras `trim()`.
- Validation Rules:
  - `clave` no puede quedar vacía después de normalización.
  - `nombre` no puede quedar vacío después de normalización.
  - Ambos campos se validan sobre el valor normalizado, no sobre el texto bruto.
- Relationships:
  - Puede estar referenciado por `Empleado.departamento_clave` a través de la FK ya existente.
- State Transitions:
  - `created`: alta correcta del departamento.
  - `updated`: cambio de `nombre` sobre una `clave` existente.
  - `deleted`: eliminación física del registro cuando la `clave` existe.

## Entity: PaginaDeDepartamentos

- Purpose: Representa la respuesta paginada del catálogo.
- Fields:
  - `content`: colección de `DepartamentoResponse`.
  - `page`: número de página actual, base cero.
  - `size`: tamaño solicitado o aplicado.
  - `totalElements`: total de departamentos disponibles.
  - `totalPages`: total de páginas calculadas.
  - `sort`: descriptor lógico del orden aplicado, fijo por `clave` ascendente.
- Validation Rules:
  - `page >= 0`.
  - `1 <= size <= 50`.
  - `content` debe respetar orden estable por `clave` ascendente.

## Entity: ErrorResponse

- Purpose: Modelo público de error ya usado por el proyecto para respuestas no exitosas.
- Fields:
  - `code`: código lógico del error HTTP/aplicación.
  - `message`: mensaje legible para cliente.
- Usage in this feature:
  - `400` para validaciones de entrada o paginación inválida.
  - `401` para acceso sin credenciales válidas.
  - `404` para consulta, actualización o eliminación de una `clave` inexistente.
  - `409` para intento de alta con `clave` duplicada.
