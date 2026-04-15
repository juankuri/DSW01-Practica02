# Data Model: CRUD de Empleados

## Entity: Empleado

### Description

Representa un registro de empleado administrado por el sistema.

### Fields

- `clave` (string, PK, required)
    - Source: provista por cliente.
    - Constraints:
        - No nula.
        - No vacía luego de normalización.
        - Única en el sistema.
        - Tipo SQL sugerido: `VARCHAR(100)`.
- `nombre` (string, required)
    - Constraints:
        - Longitud de entrada: 1..100.
        - Persistencia: longitud variable sin relleno artificial.
        - Tipo SQL sugerido: `VARCHAR(100)`.
- `direccion` (string, required)
    - Constraints:
        - Longitud de entrada: 1..100.
        - Persistencia: longitud variable sin relleno artificial.
        - Tipo SQL sugerido: `VARCHAR(100)`.
- `telefono` (string, required)
    - Constraints:
        - Longitud de entrada: 1..100.
        - Persistencia: longitud variable sin relleno artificial.
        - Tipo SQL sugerido: `VARCHAR(100)`.
- `version` (integer/long, required)
    - Purpose: control de concurrencia optimista.
    - Tipo SQL sugerido: `BIGINT`.

## Normalization Rules

- Recortar espacios al inicio y final en entrada (`trim`) para validación.
- Si campo descriptivo queda vacío tras recorte, rechazar como inválido.
- Si longitud recortada > 100, rechazar como inválido.
- No aplicar relleno artificial de espacios en persistencia o respuesta.

## Relationships

- No hay relaciones con otras entidades en este alcance.

## State Transitions

- `NoExiste` -> `Activo` (create)
- `Activo` -> `Activo` (update)
- `Activo` -> `NoExiste` (delete)

## Integrity Rules

- No se puede crear dos veces la misma `clave`.
- No se puede actualizar/eliminar un empleado inexistente.
- Actualizaciones concurrentes sobre la misma fila deben resolverse con conflicto de versión.

## API-Level Error Mapping (recommended)

- Duplicado de `clave`: `409 Conflict`
- Recurso no encontrado: `404 Not Found`
- Validación inválida: `400 Bad Request`
- Conflicto de concurrencia (`version`): `409 Conflict`
