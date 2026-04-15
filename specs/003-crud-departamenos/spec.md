# Feature Specification: CRUD de Departamentos

**Feature Branch**: `003-crud-departamenos`  
**Created**: 2026-03-10  
**Status**: Draft  
**Input**: User description: "Departamentos CRUD with clave and nombre"

## Clarifications

### Session 2026-03-10

- Q: ¿Qué formato de versionado deben usar las rutas públicas del CRUD de departamentos? → A: Usar `/api/v1/departamentos` para todas las rutas del CRUD.
- Q: ¿Qué paginación debe usar el listado de departamentos? → A: `page=0`, `size=10`, máximo `50`.
- Q: ¿Qué debe devolver el sistema al eliminar una `clave` inexistente? → A: `404 Not Found`.
- Q: ¿En qué orden debe devolverse el listado paginado de departamentos? → A: Ordenar siempre por `clave` ascendente.

## User Scenarios & Testing _(mandatory)_

### User Story 1 - Registrar departamento (Priority: P1)

Como usuario administrador, quiero registrar un departamento con su `clave` y `nombre`,
para disponer de un catálogo base de departamentos que pueda usarse en la operación diaria.

**Why this priority**: Sin el alta de departamentos no existe catálogo que consultar, actualizar o eliminar,
por lo que esta historia entrega el primer valor de negocio.

**Independent Test**: Se valida registrando un departamento nuevo con datos válidos sobre
`/api/v1/departamentos` y comprobando que queda disponible para su consulta posterior con los mismos
valores normalizados.

**Acceptance Scenarios**:

1. **Given** que la `clave` no existe previamente, **When** el administrador registra un departamento
  con `clave` y `nombre` válidos en `/api/v1/departamentos`, **Then** el sistema guarda el
  departamento y confirma el alta.
2. **Given** que la `clave` ya existe, **When** el administrador intenta registrar otro departamento
   con la misma `clave`, **Then** el sistema rechaza la operación e informa conflicto por duplicado.


### User Story 2 - Consultar departamentos (Priority: P2)

Como usuario administrador, quiero consultar un departamento por `clave` y revisar el catálogo en páginas,
para localizar registros concretos y navegar el catálogo sin respuestas no acotadas.

**Why this priority**: La consulta permite validar el catálogo cargado y la paginación es obligatoria
para cumplir la constitución del proyecto en endpoints de listado.

**Independent Test**: Se valida consultando por `clave` existente y no existente en
`/api/v1/departamentos/{clave}`, y solicitando distintas páginas del listado en `/api/v1/departamentos`
para comprobar resultados y metadatos coherentes.

**Acceptance Scenarios**:

1. **Given** que existe un departamento con `clave` válida, **When** se consulta por su `clave`,
   **Then** el sistema devuelve los datos completos del departamento.
2. **Given** que no existe la `clave` solicitada, **When** se consulta ese departamento,
   **Then** el sistema responde que no existe.
3. **Given** que existen múltiples departamentos registrados, **When** se solicita una página del listado
   con parámetros válidos, **Then** el sistema devuelve solo los registros de esa página junto con
  metadatos de paginación y orden estable por `clave` ascendente.


### User Story 3 - Actualizar y eliminar departamento (Priority: P3)

Como usuario administrador, quiero actualizar el `nombre` de un departamento existente y eliminar departamentos,
para mantener vigente el catálogo administrativo.

**Why this priority**: Completa el ciclo CRUD y permite mantener la calidad del catálogo una vez cargado.

**Independent Test**: Se valida actualizando el `nombre` de una `clave` existente en
`/api/v1/departamentos/{clave}` y eliminando un departamento por esa misma ruta para confirmar que deja
de aparecer en las consultas posteriores.

**Acceptance Scenarios**:

1. **Given** que existe un departamento, **When** se actualiza su `nombre` con un valor válido,
   **Then** el sistema persiste el cambio y devuelve el registro actualizado.
2. **Given** que no existe un departamento con una `clave` determinada, **When** se solicita
  la eliminación por `clave`, **Then** el sistema responde `404 Not Found`.

### Edge Cases

- Intento de alta o actualización con `clave` o `nombre` vacío.
- Intento de alta o actualización con `clave` o `nombre` que, tras eliminar espacios extremos, queda vacío.
- Intento de alta o actualización con `clave` o `nombre` mayor a 100 caracteres.
- Intento de consultar o actualizar una `clave` inexistente.
- Intento de eliminar una `clave` inexistente; la operación debe responder `404 Not Found`.
- Solicitud de listado cuando no existen departamentos registrados.
- Solicitud de listado con `page` negativo, `size` igual a cero o `size` mayor a `50`.
- Solicitud de listado con registros existentes; el orden debe mantenerse estable por `clave` ascendente
  entre páginas consecutivas.
- Uso de espacios al inicio o al final en `clave` o `nombre`; el sistema debe tratarlos de forma consistente.

## Requirements _(mandatory)_

### Functional Requirements

- **FR-001**: El sistema MUST permitir crear departamentos con los campos `clave` y `nombre`.
- **FR-002**: `clave` MUST ser un identificador string único provisto por el cliente.
- **FR-003**: `clave` MUST actuar como identificador primario del departamento.
- **FR-004**: `clave` y `nombre` MUST aceptar valores normalizados de entre 1 y 100 caracteres.
- **FR-005**: El sistema MUST normalizar `clave` y `nombre` eliminando espacios al inicio y al final
  antes de validar, persistir y devolver los valores.
- **FR-006**: El sistema MUST rechazar operaciones de alta o actualización cuando `clave` o `nombre`
  sean inválidos después de la normalización.
- **FR-007**: El sistema MUST permitir consultar un departamento por `clave` mediante
  `/api/v1/departamentos/{clave}`.
- **FR-008**: El sistema MUST permitir listar departamentos mediante la ruta pública
  `/api/v1/departamentos` y parámetros explícitos de paginación `page` y `size`.
- **FR-009**: El sistema MUST devolver metadatos de paginación junto con cada listado de departamentos.
- **FR-010**: El sistema MUST devolver el listado paginado ordenado por `clave` ascendente.
- **FR-011**: El sistema MUST usar `page=0` y `size=10` como valores por defecto cuando el cliente no
  envíe parámetros de paginación.
- **FR-012**: El sistema MUST definir y documentar `size=50` como tamaño máximo permitido para el
  listado de departamentos.
- **FR-013**: El sistema MUST rechazar solicitudes de listado cuyos parámetros de paginación queden
  fuera de los límites documentados.
- **FR-014**: El sistema MUST rechazar solicitudes de listado con `page` menor que `0`, `size` menor
  que `1` o `size` mayor que `50`.
- **FR-015**: El sistema MUST permitir actualizar el `nombre` de un departamento existente identificado
  por `clave` mediante `/api/v1/departamentos/{clave}`.
- **FR-016**: El sistema MUST responder `404 Not Found` cuando se solicite eliminar un departamento
  cuya `clave` no exista.
- **FR-017**: El acceso a las operaciones CRUD MUST respetar las reglas de autenticación definidas por
  la constitución del proyecto.
- **FR-018**: El contrato público del CRUD de departamentos MUST publicarse usando `/api/v1/departamentos`
  y `/api/v1/departamentos/{clave}`, y quedar reflejado en la documentación del API.

### Constitution Alignment _(mandatory)_

- **CA-001**: La especificación MUST declarar que la mejora se ejecuta sobre Java 17 y Spring Boot 3.x.
- **CA-002**: La especificación MUST declarar el impacto en HTTP Basic para todos los endpoints no públicos.
- **CA-003**: La especificación MUST declarar el impacto en persistencia PostgreSQL y ejecución local
  mediante Docker o Docker Compose.
- **CA-004**: La especificación MUST declarar el impacto en Swagger/OpenAPI para altas, consultas,
  actualización, eliminación y listado paginado.
- **CA-005**: La especificación MUST incluir estrategia de pruebas para contrato, seguridad,
  persistencia y validaciones de negocio.
- **CA-006**: La especificación MUST establecer `/api/v1/departamentos` y
  `/api/v1/departamentos/{clave}` como rutas públicas del CRUD de departamentos.
- **CA-007**: La especificación MUST establecer paginación obligatoria para el endpoint de listado.

### Key Entities _(include if feature involves data)_

- **Departamento**: Registro administrativo identificado por `clave` única, con `nombre` como dato
  descriptivo principal del catálogo.
- **PaginaDeDepartamentos**: Resultado de consulta de catálogo que incluye una colección parcial de
  departamentos y metadatos para navegar el listado paginado.

## Assumptions

- La ruta pública vigente del CRUD de departamentos será `/api/v1/departamentos` y sus variantes por `clave`.
- `clave` y `nombre` siguen la misma regla de normalización ya usada en el proyecto: eliminar espacios
  al inicio y al final y validar sobre el valor normalizado.
- `clave` no requiere un patrón adicional aparte de ser única dentro del catálogo.
- La eliminación de una `clave` inexistente se tratará como error de recurso no encontrado.
- El listado paginado usará `page=0`, `size=10` por defecto y `size=50` como máximo permitido.
- El listado paginado devolverá resultados ordenados por `clave` ascendente.

## Dependencies

- Existe un mecanismo activo de autenticación HTTP Basic para endpoints no públicos.
- Existe almacenamiento persistente en PostgreSQL para conservar departamentos entre sesiones.
- Existe un contrato OpenAPI/Swagger del servicio que puede actualizarse con este CRUD.
- Existe ejecución local con Docker o Docker Compose para mantener paridad de entorno.

## Success Criteria _(mandatory)_

### Measurable Outcomes

- **SC-001**: El 100% de operaciones CRUD válidas sobre departamentos se completa correctamente en
  pruebas funcionales.
- **SC-002**: El 100% de intentos con `clave` o `nombre` inválidos tras la normalización se rechaza
  con un mensaje claro.
- **SC-003**: El 100% de respuestas del listado de departamentos incluye metadatos de paginación y
  respeta el tamaño solicitado dentro de los límites documentados.
- **SC-004**: El 100% de listados paginados devuelve resultados ordenados por `clave` ascendente.
- **SC-005**: Al menos 95% de consultas de departamento por `clave` y de listados paginados responden
  en menos de 2 segundos bajo carga operativa normal.
- **SC-006**: Al menos 90% de usuarios administradores completa el flujo de alta y consulta de un
  departamento al primer intento durante validación de aceptación.
- **SC-007**: El 100% de pruebas de eliminación sobre una `clave` inexistente responde `404 Not Found`.
- **SC-008**: El 100% de pruebas automatizadas definidas para seguridad, contrato, persistencia y
  reglas de validación finaliza correctamente antes de aprobar la funcionalidad.
