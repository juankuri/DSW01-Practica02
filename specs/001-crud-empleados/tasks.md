# Tasks: CRUD de Empleados

**Input**: Design documents from `/specs/001-crud-empleados/`  
**Prerequisites**: plan.md (required), spec.md (required for user stories), research.md, data-model.md, contracts/

**Tests**: Se incluyen tareas de pruebas porque la especificación/constitución exige cobertura automatizada para contrato, seguridad y persistencia.

**Organization**: Tasks are grouped by user story to enable independent implementation and testing of each story.

## Phase 1: Setup (Shared Infrastructure)

**Purpose**: Inicialización de proyecto y base técnica para backend Spring Boot.

- [x] T001 Inicializar proyecto Spring Boot 3 con Java 17 y dependencias en pom.xml
- [x] T002 Crear estructura de paquetes base en src/main/java/com/dsw01/practica02/
- [x] T003 [P] Configurar properties base (DB/Security/Swagger) en src/main/resources/application.properties
- [x] T004 [P] Definir docker-compose para PostgreSQL local en docker-compose.yml

---

## Phase 2: Foundational (Blocking Prerequisites)

**Purpose**: Infraestructura bloqueante antes de implementar historias de usuario.

**⚠️ CRITICAL**: No user story work can begin until this phase is complete

- [x] T005 Crear migración inicial de tabla empleados en src/main/resources/db/migration/V1\_\_create_empleado_table.sql
- [x] T006 [P] Implementar configuración HTTP Basic y rutas públicas Swagger/health en src/main/java/com/dsw01/practica02/config/SecurityConfig.java
- [x] T007 [P] Implementar configuración OpenAPI/Swagger en src/main/java/com/dsw01/practica02/config/OpenApiConfig.java
- [x] T008 Crear manejo global de excepciones HTTP en src/main/java/com/dsw01/practica02/web/GlobalExceptionHandler.java
- [x] T009 [P] Crear payload estándar de error en src/main/java/com/dsw01/practica02/web/dto/ErrorResponse.java
- [x] T010 Actualizar data model a longitud variable sin padding en specs/001-crud-empleados/data-model.md
- [x] T011 [P] Actualizar contrato OpenAPI (`clave` maxLength 100 y delete inexistente 204) en specs/001-crud-empleados/contracts/empleados.openapi.yaml
- [x] T012 [P] Actualizar research/quickstart para eliminar referencias a padding fijo en specs/001-crud-empleados/research.md

**Checkpoint**: Foundation ready - user story implementation can now begin in parallel

---

## Phase 3: User Story 1 - Registrar empleado (Priority: P1) 🎯 MVP

**Goal**: Dar de alta empleados con `clave` única y campos válidos de longitud variable (1..100).

**Independent Test**: Crear empleado válido retorna 201 y crear duplicado por `clave` retorna 409.

### Tests for User Story 1

- [x] T013 [P] [US1] Crear test de contrato POST /api/empleados en src/test/java/com/dsw01/practica02/contract/EmpleadoCreateContractTest.java
- [x] T014 [P] [US1] Crear test de integración para duplicado de clave en src/test/java/com/dsw01/practica02/integration/EmpleadoCreateIntegrationTest.java

### Implementation for User Story 1

- [x] T015 [US1] Crear entidad Empleado con `@Version` en src/main/java/com/dsw01/practica02/domain/Empleado.java
- [x] T016 [P] [US1] Crear repositorio JPA de Empleado en src/main/java/com/dsw01/practica02/repository/EmpleadoRepository.java
- [x] T017 [P] [US1] Crear DTOs de alta y respuesta en src/main/java/com/dsw01/practica02/web/dto/EmpleadoCreateRequest.java
- [x] T018 [P] [US1] Crear DTO de salida en src/main/java/com/dsw01/practica02/web/dto/EmpleadoResponse.java
- [x] T019 [US1] Implementar servicio de creación y validaciones en src/main/java/com/dsw01/practica02/service/EmpleadoService.java
- [x] T020 [US1] Implementar endpoint POST /api/empleados en src/main/java/com/dsw01/practica02/web/EmpleadoController.java

**Checkpoint**: User Story 1 funcional e independiente

---

## Phase 4: User Story 2 - Consultar empleados (Priority: P2)

**Goal**: Consultar empleado por `clave` y listar catálogo completo.

**Independent Test**: GET por clave existente retorna 200; GET por clave inexistente retorna 404; listado retorna 200.

### Tests for User Story 2

- [x] T021 [P] [US2] Crear test de contrato GET /api/empleados/{clave} y GET /api/empleados en src/test/java/com/dsw01/practica02/contract/EmpleadoQueryContractTest.java
- [x] T022 [P] [US2] Crear test de integración para consulta inexistente en src/test/java/com/dsw01/practica02/integration/EmpleadoQueryIntegrationTest.java

### Implementation for User Story 2

- [x] T023 [US2] Implementar consulta por clave en servicio en src/main/java/com/dsw01/practica02/service/EmpleadoService.java
- [x] T024 [US2] Implementar listado completo en servicio en src/main/java/com/dsw01/practica02/service/EmpleadoService.java
- [x] T025 [US2] Implementar endpoint GET /api/empleados/{clave} en src/main/java/com/dsw01/practica02/web/EmpleadoController.java
- [x] T026 [US2] Implementar endpoint GET /api/empleados en src/main/java/com/dsw01/practica02/web/EmpleadoController.java

**Checkpoint**: User Stories 1 y 2 funcionales independientemente

---

## Phase 5: User Story 3 - Actualizar y eliminar empleado (Priority: P3)

**Goal**: Actualizar con control de concurrencia y eliminar de forma idempotente (`204` incluso si no existe).

**Independent Test**: PUT con versión desactualizada retorna 409; DELETE de clave existente e inexistente retorna 204.

### Tests for User Story 3

- [x] T027 [P] [US3] Crear test de contrato PUT/DELETE por clave en src/test/java/com/dsw01/practica02/contract/EmpleadoUpdateDeleteContractTest.java
- [x] T028 [P] [US3] Crear test de integración de conflicto optimista en src/test/java/com/dsw01/practica02/integration/EmpleadoConcurrencyIntegrationTest.java
- [x] T029 [P] [US3] Crear test de integración para delete idempotente 204 en src/test/java/com/dsw01/practica02/integration/EmpleadoDeleteIntegrationTest.java

### Implementation for User Story 3

- [x] T030 [US3] Crear DTO de actualización con version requerida en src/main/java/com/dsw01/practica02/web/dto/EmpleadoUpdateRequest.java
- [x] T031 [US3] Implementar actualización con validación de versión en src/main/java/com/dsw01/practica02/service/EmpleadoService.java
- [x] T032 [US3] Implementar eliminación idempotente por clave en src/main/java/com/dsw01/practica02/service/EmpleadoService.java
- [x] T033 [US3] Implementar endpoint PUT /api/empleados/{clave} en src/main/java/com/dsw01/practica02/web/EmpleadoController.java
- [x] T034 [US3] Implementar endpoint DELETE /api/empleados/{clave} con 204 idempotente en src/main/java/com/dsw01/practica02/web/EmpleadoController.java

**Checkpoint**: Todas las historias funcionales e independientes

---

## Phase 6: Polish & Cross-Cutting Concerns

**Purpose**: Ajustes finales transversales y validación integral.

- [x] T035 [P] Actualizar documentación de ejecución y ejemplos curl en specs/001-crud-empleados/quickstart.md
- [x] T036 [P] Alinear contrato OpenAPI final con implementación en specs/001-crud-empleados/contracts/empleados.openapi.yaml
- [x] T037 Ejecutar suite de pruebas y corregir regresiones en src/test/java/com/dsw01/practica02/
- [x] T038 Verificar cumplimiento de criterios de éxito en specs/001-crud-empleados/spec.md

---

## Dependencies & Execution Order

### Phase Dependencies

- **Phase 1 (Setup)**: sin dependencias.
- **Phase 2 (Foundational)**: depende de Phase 1 y bloquea historias.
- **Phase 3-5 (User Stories)**: dependen de Phase 2.
- **Phase 6 (Polish)**: depende de historias completadas.

### User Story Dependencies

- **US1 (P1)**: inicia al completar Phase 2; no depende de otras historias.
- **US2 (P2)**: inicia al completar Phase 2; usa base de US1 pero mantiene prueba independiente.
- **US3 (P3)**: inicia al completar Phase 2; depende funcionalmente de operaciones de US1 para pruebas de actualización/eliminación.

### Within Each User Story

- Tests primero (contrato/integración).
- DTOs y modelo antes de servicio.
- Servicio antes de controlador/endpoints.
- Validar criterio independiente al finalizar cada historia.

### Story Completion Order (Graph)

- US1 → US2 → US3 (orden recomendado de entrega incremental)
- US2 y US3 pueden avanzar en paralelo por equipo una vez completada la base compartida, manteniendo sus pruebas independientes.

### Parallel Opportunities

- Setup: T003, T004 en paralelo.
- Foundational: T006, T007, T009, T011, T012 en paralelo.
- US1: T013, T014, T016, T017, T018 en paralelo por archivo.
- US2: T021, T022 en paralelo.
- US3: T027, T028, T029 en paralelo.
- Polish: T035, T036 en paralelo.

---

## Parallel Example: User Story 1

```bash
# Tests en paralelo:
Task: "T013 [US1] Contract test POST empleados"
Task: "T014 [US1] Integration test duplicado clave"

# Artefactos de modelo/DTO en paralelo:
Task: "T016 [US1] EmpleadoRepository"
Task: "T017 [US1] EmpleadoCreateRequest"
Task: "T018 [US1] EmpleadoResponse"
```

## Parallel Example: User Story 2

```bash
# Tests de consulta en paralelo:
Task: "T021 [US2] Contract tests GET"
Task: "T022 [US2] Integration test consulta inexistente"
```

## Parallel Example: User Story 3

```bash
# Tests de actualización/eliminación en paralelo:
Task: "T027 [US3] Contract tests PUT/DELETE"
Task: "T028 [US3] Integration test conflicto optimista"
Task: "T029 [US3] Integration test delete idempotente"
```

---

## Implementation Strategy

### MVP First (User Story 1 Only)

1. Completar Phase 1 y Phase 2.
2. Completar Phase 3 (US1).
3. Validar alta exitosa + conflicto de duplicado.
4. Entregar MVP.

### Incremental Delivery

1. Base técnica compartida (Phases 1-2).
2. Entregar US1.
3. Agregar US2 sin romper US1.
4. Agregar US3 con control de concurrencia e idempotencia.
5. Ejecutar polish final.

### Parallel Team Strategy

1. Equipo completo en Setup + Foundational.
2. Luego:
    - Dev A: US1
    - Dev B: US2
    - Dev C: US3
3. Integración y cierre en Phase 6.
