# Phase 0 - Research: CRUD de Empleados

## Decision 1: Estrategia de longitud variable (máximo 100) para `nombre`, `direccion`, `telefono`

- Decision: Persistir `nombre`, `direccion` y `telefono` en columnas `VARCHAR(100)` sin aplicar relleno artificial de espacios.
- Rationale: La especificación clarificada exige longitud variable con máximo 100 y respuesta sin padding.
- Alternatives considered:
    - `CHAR(100)` + padding: contradice la clarificación final.
    - `TEXT` + validación manual: agrega riesgo de inconsistencias y no expresa la restricción en el esquema.

## Decision 2: Identificador `clave` como PK string enviada por cliente

- Decision: Usar `clave` como PK de tipo string (`VARCHAR(100)`), requerida y única, enviada por el cliente en alta.
- Rationale: La especificación clarificada exige que `clave` sea string libre y provista por cliente, manteniendo unicidad.
- Alternatives considered:
    - PK autoincremental numérica: contradice la clarificación aceptada.
    - UUID generado por servidor: contradice control explícito de clave por cliente.

## Decision 3: Manejo de concurrencia en actualización

- Decision: Aplicar control optimista con campo de versión (`@Version`) y responder conflicto cuando exista escritura concurrente.
- Rationale: Cumple FR-011 (consistencia en concurrencia) sin bloqueos pesados y permite reportar conflictos de forma explícita a clientes.
- Alternatives considered:
    - Last-write-wins: simplifica implementación pero puede perder cambios silenciosamente.
    - Bloqueo pesimista: reduce conflictos lógicos pero penaliza rendimiento y escalabilidad.

## Decision 4: Seguridad de endpoints

- Decision: Proteger endpoints CRUD con HTTP Basic y rol `ADMIN`; permitir acceso público solo a endpoints de OpenAPI/Swagger y health.
- Rationale: Cumple la constitución del proyecto y mantiene trazabilidad de accesos en un backend administrativo.
- Alternatives considered:
    - Exponer endpoints sin autenticación: incumple principio constitucional de seguridad.
    - OAuth2/JWT: fuera de alcance actual y no requerido por la constitución vigente.

## Decision 5: Persistencia y ejecución local

- Decision: PostgreSQL como base de datos única para desarrollo y pruebas de integración, ejecutada con Docker/Docker Compose.
- Rationale: Mantiene paridad con entorno objetivo y cumple el principio constitucional de persistencia con Docker.
- Alternatives considered:
    - H2 en memoria para todo: útil para rapidez, pero rompe paridad de comportamiento SQL y tipos.
    - Motor alternativo (MySQL): no alineado con constitución.

## Decision 6: Contrato y documentación API

- Decision: Definir contrato REST para CRUD completo (`POST`, `GET`, `GET by clave`, `PUT`, `DELETE`) con eliminación idempotente (`204` para clave inexistente) y mantener OpenAPI/Swagger sincronizado en cada cambio.
- Rationale: Cumple principio API-first y facilita validación de escenarios de usuario y pruebas de contrato.
- Alternatives considered:
    - Documentación manual fuera de OpenAPI: propensa a desalineación.
    - Exponer solo endpoints mínimos sin contrato formal: reduce trazabilidad y verificabilidad.

## Decision 7: Estrategia de pruebas

- Decision: Combinar pruebas unitarias (servicios/validaciones), integración (repositorio + PostgreSQL) y contrato/controlador (MockMvc + seguridad).
- Rationale: Cubre criterios constitucionales de seguridad, persistencia y contrato sin sobredimensionar el alcance.
- Alternatives considered:
    - Solo pruebas unitarias: insuficiente para validar seguridad y persistencia real.
    - Solo pruebas end-to-end: mayor costo y menor precisión diagnóstica.
