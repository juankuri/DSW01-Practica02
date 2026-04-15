# Research: CRUD de Departamentos

## Decision 1: Migrar el contrato público a `/api/v1/departamentos`

- Decision: Publicar todas las operaciones del CRUD bajo `/api/v1/departamentos` y `/api/v1/departamentos/{clave}`.
- Rationale: La constitución exige rutas versionadas para endpoints públicos; el código actual usa `/api/departamentos`, por lo que la implementación del feature debe corregir explícitamente ese desajuste.
- Alternatives considered: Mantener `/api/departamentos` fue descartado por incumplimiento constitucional. Soportar ambas rutas se descartó para evitar duplicidad de contrato y pruebas innecesarias en esta fase.

## Decision 2: Implementar paginación estable con `page=0`, `size=10`, máximo `50`, orden por `clave` ascendente

- Decision: El endpoint de listado aceptará `page` y `size`, devolverá metadatos de paginación y mantendrá orden fijo por `clave` ascendente.
- Rationale: La constitución exige paginación documentada; un orden fijo por PK evita resultados inestables entre páginas y simplifica pruebas de contrato e integración.
- Alternatives considered: Listado completo sin paginación fue descartado por incumplir la constitución. Orden configurable por cliente se descartó por ampliar el contrato sin valor requerido por la especificación.

## Decision 3: Responder `404 Not Found` al eliminar una clave inexistente

- Decision: `DELETE /api/v1/departamentos/{clave}` devolverá `404 Not Found` cuando el recurso no exista.
- Rationale: La especificación clarificada fijó este comportamiento y obliga a cambiar la implementación actual, que hoy elimina de manera idempotente con `204` aunque el registro no exista.
- Alternatives considered: Mantener `204` idempotente se descartó porque contradice la decisión de clarificación aceptada para este feature.

## Decision 4: Reutilizar el esquema SQL y la normalización existentes

- Decision: Mantener la tabla `departamentos` actual, sin nuevas migraciones, reutilizando la validación y normalización por `trim()` de `clave` y `nombre`.
- Rationale: El esquema existente ya usa `clave VARCHAR(100) PRIMARY KEY` y `nombre VARCHAR(100) NOT NULL`; la lógica actual ya recorta espacios extremos y valida longitudes. El cambio real del feature está en contrato, pruebas y comportamiento de delete/listado.
- Alternatives considered: Introducir nuevas columnas o migraciones se descartó por no aportar valor al contrato definido ni a los criterios de éxito.

## Decision 5: Ampliar pruebas de contrato, seguridad y persistencia sobre la superficie de migración

- Decision: Extender las pruebas para cubrir rutas versionadas, paginación, orden ascendente, límites de `page`/`size`, y delete `404`, además de mantener cobertura de autenticación HTTP Basic.
- Rationale: El repositorio ya usa MockMvc y Spring Security Test para contrato; la nueva semántica del endpoint requiere actualizar tanto pruebas de controlador como integración y servicio.
- Alternatives considered: Limitarse a pruebas unitarias se descartó porque la constitución exige cobertura de reglas de seguridad, contrato y DB interactions al menos por una ruta automatizada.
