# Feature Specification: CRUD de Empleados

**Feature Branch**: `001-crud-empleados`  
**Created**: 2026-02-25  
**Status**: Draft  
**Input**: User description: "crear un crud de empleados con los campos clave: nombre, direccion y telefono. Donde clave sea el PK y los demas campos sean de 100 espacios."

## Clarifications

### Session 2026-02-25

- Q: ¿Cómo interpretar “los demás campos sean de 100 espacios” para `nombre`, `direccion` y `telefono`? → A: Longitud variable, sin relleno; máximo 100 caracteres.
- Q: ¿Qué regla debe seguir `clave` (PK) para creación de empleados? → A: `clave` la envía el cliente como string libre y única.
- Q: Para cumplir `FR-011` en concurrencia, ¿cómo debe comportarse el sistema cuando dos actualizaciones compiten sobre la misma `clave`? → A: Rechazar la segunda actualización con conflicto por versión/dato desactualizado.
- Q: Cuando se consulten empleados (`GET`), ¿cómo deben devolverse `nombre`, `direccion` y `telefono`? → A: Sin rellenar espacios; longitud variable y nunca mayor a 100.
- Q: ¿Qué longitud máxima debe tener `clave` (string PK enviada por cliente)? → A: Máximo 100 caracteres.
- Q: Cuando se intente eliminar una `clave` que no existe, ¿qué debe devolver la API? → A: `204 No Content`.

## User Scenarios & Testing _(mandatory)_

### User Story 1 - Registrar empleado (Priority: P1)

Como usuario administrador, quiero registrar un empleado con su clave, nombre, dirección y teléfono,
para poder iniciar el catálogo base de empleados.

**Why this priority**: Sin alta de empleados no existe información para consultar, actualizar ni eliminar,
por lo que es el primer valor de negocio.

**Independent Test**: Se valida creando un empleado nuevo con datos válidos y verificando que queda
disponible para consulta con los mismos valores capturados.

**Acceptance Scenarios**:

1. **Given** que la clave no existe previamente, **When** se registra un empleado con `nombre`,
   `direccion` y `telefono` válidos, **Then** el sistema guarda el empleado y confirma el alta.
2. **Given** que la clave ya existe, **When** se intenta registrar otro empleado con la misma clave,
   **Then** el sistema rechaza la operación e informa conflicto de clave duplicada.

---

### User Story 2 - Consultar empleados (Priority: P2)

Como usuario administrador, quiero consultar un empleado por clave y listar todos los empleados,
para verificar y revisar la información registrada.

**Why this priority**: Después del alta, la consulta permite validar calidad de datos y dar visibilidad
al catálogo.

**Independent Test**: Se valida consultando por clave existente y no existente, y solicitando listado
general para confirmar resultados consistentes.

**Acceptance Scenarios**:

1. **Given** que existe un empleado con clave válida, **When** se consulta por su clave,
   **Then** el sistema devuelve sus datos completos.
2. **Given** que no existe la clave solicitada, **When** se consulta ese empleado,
   **Then** el sistema responde que no existe.
3. **Given** que hay empleados registrados, **When** se solicita el listado,
   **Then** el sistema devuelve todos los empleados sin duplicados.

---

### User Story 3 - Actualizar y eliminar empleado (Priority: P3)

Como usuario administrador, quiero actualizar datos de un empleado existente y eliminar empleados,
para mantener el catálogo vigente.

**Why this priority**: Complementa el ciclo CRUD completo y permite mantenimiento operativo del catálogo.

**Independent Test**: Se valida modificando `nombre`, `direccion` o `telefono` de una clave existente,
y posteriormente eliminando un empleado para verificar que deja de estar disponible.

**Acceptance Scenarios**:

1. **Given** que existe un empleado, **When** se actualiza su información con valores válidos,
   **Then** el sistema persiste los cambios y muestra el registro actualizado.
2. **Given** que existe un empleado, **When** se elimina por su clave,
   **Then** el sistema confirma eliminación y ya no permite recuperarlo por esa clave.

### Edge Cases

- Intento de registro o actualización con `nombre`, `direccion` o `telefono` vacío.
- Intento de registro o actualización con `nombre`, `direccion` o `telefono` mayor a 100 caracteres.
- Intento de consultar o actualizar sobre una `clave` inexistente.
- Intento de eliminar una `clave` inexistente; la operación debe responder `204 No Content` (idempotente).
- Intento de crear o actualizar con `clave` mayor a 100 caracteres.
- Solicitud de listado cuando no existen empleados registrados.
- Uso de espacios al inicio o final en campos de texto; el sistema debe tratarlos de forma consistente.
- Dos actualizaciones concurrentes sobre la misma `clave`; la segunda debe fallar con conflicto por versión.

## Requirements _(mandatory)_

### Functional Requirements

- **FR-001**: El sistema MUST permitir crear empleados con los campos `clave`, `nombre`, `direccion`
  y `telefono`.
- **FR-002**: `clave` MUST ser un identificador tipo string libre, provisto por el cliente.
- **FR-003**: `clave` MUST ser única y actuar como identificador primario del empleado.
- **FR-004**: `clave` MUST tener longitud máxima de 100 caracteres.
- **FR-005**: El sistema MUST permitir consultar un empleado por `clave`.
- **FR-006**: El sistema MUST permitir listar todos los empleados registrados.
- **FR-007**: El sistema MUST permitir actualizar `nombre`, `direccion` y `telefono` de un empleado
  existente identificado por `clave`.
- **FR-008**: El sistema MUST permitir eliminar un empleado existente identificado por `clave`.
- **FR-009**: El sistema MUST responder `204 No Content` cuando se solicite eliminar una `clave`
  inexistente.
- **FR-010**: `nombre`, `direccion` y `telefono` MUST aceptar longitud variable entre 1 y 100 caracteres.
- **FR-011**: El sistema MUST conservar el valor capturado en `nombre`, `direccion` y `telefono` sin
  rellenar espacios artificiales.
- **FR-012**: El sistema MUST rechazar operaciones con datos inválidos y devolver mensajes claros.
- **FR-013**: El sistema MUST mantener consistencia de datos tras operaciones concurrentes sobre la
  misma `clave`, rechazando la segunda actualización cuando detecte versión o estado desactualizado.
- **FR-014**: El acceso a operaciones CRUD MUST respetar las reglas de autenticación definidas por la
  constitución del proyecto.

### Constitution Alignment _(mandatory)_

- **CA-001**: La especificación MUST declarar impacto en seguridad de acceso para todos los endpoints
  de este CRUD.
- **CA-002**: La especificación MUST declarar impacto en persistencia de datos y ejecución local en
  contenedor.
- **CA-003**: La especificación MUST declarar impacto en documentación del contrato de API.
- **CA-004**: La especificación MUST incluir estrategia de pruebas para validar comportamiento funcional,
  seguridad y persistencia.

### Key Entities _(include if feature involves data)_

- **Empleado**: Registro de personal identificado por `clave` (única), con atributos `nombre`,
  `direccion` y `telefono`; `clave` es string libre provista por cliente con máximo 100 caracteres y los tres atributos
  descriptivos tienen longitud variable con máximo 100 caracteres.

## Assumptions

- `clave` no tiene patrón restringido adicional más allá de ser string única dentro del catálogo.
- El CRUD está orientado a administración interna y no requiere versionado histórico de cambios.
- El sistema devuelve mensajes de error entendibles para entradas inválidas y recursos inexistentes.

## Dependencies

- Existe un mecanismo de autenticación activo para proteger las operaciones de administración.
- Existe un almacenamiento persistente disponible para conservar empleados entre sesiones.
- Existe un canal de documentación de API actualizado para publicar contratos de este CRUD.

## Success Criteria _(mandatory)_

### Measurable Outcomes

- **SC-001**: El 100% de operaciones CRUD válidas sobre empleados se completan correctamente en
  pruebas funcionales.
- **SC-002**: El 100% de intentos con `nombre`, `direccion` o `telefono` mayores a 100 caracteres son
  rechazados con mensaje claro.
- **SC-003**: El 100% de entradas válidas para `nombre`, `direccion` o `telefono` se persisten y se
  devuelven sin relleno artificial, manteniendo longitud menor o igual a 100 caracteres.
- **SC-004**: Al menos 95% de consultas de empleado por `clave` finalizan en menos de 2 segundos bajo
  carga operativa normal.
- **SC-005**: Al menos 90% de usuarios administradores completan el flujo de alta y consulta de empleado
  al primer intento durante validación de aceptación.
- **SC-006**: El 100% de pruebas de actualización concurrente sobre la misma `clave` devuelven conflicto
  para la segunda operación cuando existe versión desactualizada.
- **SC-007**: El 100% de pruebas de eliminación sobre `clave` inexistente responden `204 No Content`.
