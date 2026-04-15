# Tasks: Angular Frontend - Login y CRUD de Empleados y Departamentos

**Input**: Design documents from `/specs/004-frontend-empleados-login/`
**Prerequisites**: plan.md, spec.md, research.md, data-model.md, contracts/

**Tests**: Se incluyen tareas de pruebas porque la especificacion exige cobertura unitaria + E2E para login y CRUD.

## Phase 1: Setup (Project Initialization)

- [x] T001 Scaffold Angular 21 app in frontend/ using `ng new` with routing and SCSS in frontend/angular.json
- [x] T002 [P] Create frontend module folders for auth/empleados/departamentos in frontend/src/app/features/
- [x] T003 [P] Configure API base URL and feature flags in frontend/src/environments/environment.ts and frontend/src/environments/environment.prod.ts
- [x] T004 [P] Add frontend container files in frontend/Dockerfile and frontend/nginx.conf
- [x] T005 Update compose stack to include frontend service in docker-compose.yml

## Phase 2: Foundational (Blocking Prerequisites)

- [x] T006 Add backend CORS allowlist for Angular/nginx origins in src/main/java/com/dsw01/practica02/config/SecurityConfig.java
- [x] T007 Add versioned compatibility aliases for empleados routes in src/main/java/com/dsw01/practica02/web/EmpleadoController.java
- [x] T008 Add versioned compatibility aliases for departamentos routes in src/main/java/com/dsw01/practica02/web/DepartamentoController.java
- [x] T009 Implement paginated empleados endpoint (`page`,`size`) in src/main/java/com/dsw01/practica02/web/EmpleadoController.java
- [x] T010 Implement paginated departamentos endpoint (`page`,`size`) in src/main/java/com/dsw01/practica02/web/DepartamentoController.java
- [x] T011 Update OpenAPI annotations for versioned/paginated routes in src/main/java/com/dsw01/practica02/config/OpenApiConfig.java
- [x] T012 [P] Implement session credentials service in frontend/src/app/core/services/auth-session.service.ts
- [x] T013 [P] Implement HTTP Basic interceptor in frontend/src/app/core/interceptors/basic-auth.interceptor.ts
- [x] T014 [P] Implement 401 handler + normalized messages in frontend/src/app/core/services/api-error.service.ts
- [x] T015 [P] Implement route guard for protected pages in frontend/src/app/core/guards/auth.guard.ts
- [x] T016 [P] Implement shared paged response models in frontend/src/app/core/models/paged-result.model.ts

## Phase 3: User Story 1 - Login con HTTP Basic (Priority: P1)

**Goal**: Permitir login con usuario/password y proteger rutas.
**Independent Test**: Sin credenciales redirige a /login; con credenciales validas entra a /empleados y agrega Authorization en llamadas.

- [x] T017 [P] [US1] Add auth session unit tests in frontend/src/app/core/services/auth-session.service.spec.ts
- [x] T018 [P] [US1] Add basic auth interceptor unit tests in frontend/src/app/core/interceptors/basic-auth.interceptor.spec.ts
- [x] T019 [P] [US1] Add route guard unit tests in frontend/src/app/core/guards/auth.guard.spec.ts
- [x] T020 [US1] Create login page component and template in frontend/src/app/features/auth/login/login.component.ts and frontend/src/app/features/auth/login/login.component.html
- [x] T021 [US1] Implement login form required validation (`usuario`,`password`) in frontend/src/app/features/auth/login/login.component.ts
- [x] T022 [US1] Wire login submit/logout flow in frontend/src/app/features/auth/login/login.component.ts and frontend/src/app/shared/components/topbar/topbar.component.ts
- [x] T023 [US1] Configure app routes with public /login and protected shell in frontend/src/app/app.routes.ts
- [x] T024 [US1] Add E2E login success/failure/redirect scenarios in frontend/e2e/login-auth.e2e-spec.ts

## Phase 4: User Story 2 - Listados paginados (Priority: P2)

**Goal**: Mostrar listados paginados de empleados y departamentos con metadatos de pagina.
**Independent Test**: Se navega /empleados y /departamentos, pagina y size cambian, y se renderizan page/size/totalElements.

- [x] T025 [P] [US2] Implement empleados API client with paged GET in frontend/src/app/core/services/empleados-api.service.ts
- [x] T026 [P] [US2] Implement departamentos API client with paged GET in frontend/src/app/core/services/departamentos-api.service.ts
- [x] T027 [P] [US2] Add empleados API pagination tests in frontend/src/app/core/services/empleados-api.service.spec.ts
- [x] T028 [P] [US2] Add departamentos API pagination tests in frontend/src/app/core/services/departamentos-api.service.spec.ts
- [x] T029 [US2] Create empleados list page and table in frontend/src/app/features/empleados/list/empleados-list.component.ts and frontend/src/app/features/empleados/list/empleados-list.component.html
- [x] T030 [US2] Create departamentos list page and table in frontend/src/app/features/departamentos/list/departamentos-list.component.ts and frontend/src/app/features/departamentos/list/departamentos-list.component.html
- [x] T031 [US2] Implement shared pagination controls (5/10/20) in frontend/src/app/shared/components/pagination/pagination.component.ts
- [x] T032 [US2] Render pagination metadata (`page`,`size`,`totalElements`) in frontend/src/app/features/empleados/list/empleados-list.component.html
- [x] T033 [US2] Render pagination metadata (`page`,`size`,`totalElements`) in frontend/src/app/features/departamentos/list/departamentos-list.component.html
- [x] T034 [US2] Add empty/loading/error UI states in frontend/src/app/shared/components/list-state/list-state.component.ts
- [x] T035 [US2] Add E2E coverage for employees/departments pagination in frontend/e2e/list-pagination.e2e-spec.ts

## Phase 5: User Story 3 - CRUD de Empleados (Priority: P3)

**Goal**: Crear, editar (PUT) y eliminar empleados con modales y confirmacion.
**Independent Test**: Crear-editar-eliminar empleado sin recarga completa de pagina.

- [x] T036 [P] [US3] Add empleado form validation tests in frontend/src/app/features/empleados/components/empleado-form-dialog/empleado-form-dialog.component.spec.ts
- [x] T037 [P] [US3] Add empleado API mutation tests (POST/PUT/DELETE) in frontend/src/app/core/services/empleados-api.service.spec.ts
- [x] T038 [US3] Create empleado form dialog component in frontend/src/app/features/empleados/components/empleado-form-dialog/empleado-form-dialog.component.ts
- [x] T039 [US3] Implement department selector data source in frontend/src/app/features/empleados/components/empleado-form-dialog/empleado-form-dialog.component.ts
- [x] T040 [US3] Implement create empleado flow in frontend/src/app/features/empleados/list/empleados-list.component.ts
- [x] T041 [US3] Implement edit empleado flow using PUT+version in frontend/src/app/features/empleados/list/empleados-list.component.ts
- [x] T042 [US3] Implement delete empleado confirmation dialog in frontend/src/app/features/empleados/components/empleado-delete-confirm/empleado-delete-confirm.component.ts
- [x] T043 [US3] Refresh empleados page data after each mutation in frontend/src/app/features/empleados/list/empleados-list.component.ts
- [x] T044 [US3] Add E2E empleado CRUD happy/conflict scenarios in frontend/e2e/empleados-crud.e2e-spec.ts

## Phase 6: User Story 4 - CRUD de Departamentos (Priority: P4)

**Goal**: Crear, editar (PUT) y eliminar departamentos con modales y confirmacion.
**Independent Test**: Crear-editar-eliminar departamento y reflejar cambios inmediatos en listado.

- [x] T045 [P] [US4] Add departamento form validation tests in frontend/src/app/features/departamentos/components/departamento-form-dialog/departamento-form-dialog.component.spec.ts
- [x] T046 [P] [US4] Add departamento API mutation tests (POST/PUT/DELETE) in frontend/src/app/core/services/departamentos-api.service.spec.ts
- [x] T047 [US4] Create departamento form dialog component in frontend/src/app/features/departamentos/components/departamento-form-dialog/departamento-form-dialog.component.ts
- [x] T048 [US4] Implement create departamento flow in frontend/src/app/features/departamentos/list/departamentos-list.component.ts
- [x] T049 [US4] Implement edit departamento flow via PUT in frontend/src/app/features/departamentos/list/departamentos-list.component.ts
- [x] T050 [US4] Implement delete departamento confirmation dialog in frontend/src/app/features/departamentos/components/departamento-delete-confirm/departamento-delete-confirm.component.ts
- [x] T051 [US4] Handle integrity-conflict feedback on departamento delete in frontend/src/app/features/departamentos/list/departamentos-list.component.ts
- [x] T052 [US4] Refresh departamentos page data after each mutation in frontend/src/app/features/departamentos/list/departamentos-list.component.ts
- [x] T053 [US4] Add E2E departamento CRUD scenarios in frontend/e2e/departamentos-crud.e2e-spec.ts

## Phase 7: Polish & Cross-Cutting Concerns

- [x] T054 [P] Validate quickstart end-to-end steps in specs/004-frontend-empleados-login/quickstart.md
- [x] T055 [P] Add backend regression tests for versioned aliases/pagination in src/test/java/com/dsw01/practica02/contract/
- [x] T056 [P] Add frontend Docker smoke test script in frontend/scripts/smoke-compose.sh
- [x] T057 [P] Measure and document SC timing goals in specs/004-frontend-empleados-login/quickstart.md
- [x] T058 Run full test suites (`mvn test`, frontend unit, frontend E2E) and record results in specs/004-frontend-empleados-login/quickstart.md

## Dependencies & Execution Order

### Phase Dependencies

- Phase 1 -> no dependencies.
- Phase 2 -> depends on Phase 1; blocks all user stories.
- Phase 3 (US1) -> depends on Phase 2.
- Phase 4 (US2) -> depends on Phase 2 and reuses auth from US1 for protected navigation checks.
- Phase 5 (US3) -> depends on Phase 4 (list screens and APIs in place).
- Phase 6 (US4) -> depends on Phase 4 (list screens and APIs in place).
- Phase 7 -> depends on all prior phases.

### User Story Completion Order

- US1 (P1) -> US2 (P2) -> US3 (P3) and US4 (P4) -> Polish.

## Parallel Execution Examples

### User Story 1

- Run T017, T018, T019 in parallel (independent test files).
- Run T020 and T023 in parallel (component vs route config files).

### User Story 2

- Run T025 and T026 in parallel (separate services).
- Run T029 and T030 in parallel (separate feature folders).
- Run T032 and T033 in parallel (separate templates).

### User Story 3

- Run T036 and T037 in parallel (dialog spec vs service spec).
- Run T042 in parallel with T040/T041 once dialog exists.

### User Story 4

- Run T045 and T046 in parallel (dialog spec vs service spec).
- Run T048 and T049 in sequence, while T050 can start after T047.

## Implementation Strategy

### MVP First

1. Finish Phase 1 and Phase 2.
2. Finish US1 (Phase 3) and validate authentication/guard flow.
3. Finish US2 (Phase 4) to deliver a browsable authenticated frontend.

### Incremental Delivery

1. Deliver US1 + US2 as MVP.
2. Add US3 (empleados CRUD) and validate independently.
3. Add US4 (departamentos CRUD) and validate independently.
4. Run Phase 7 hardening and performance evidence tasks before handoff.

### Parallel Team Plan

1. Team A: backend compatibility tasks T006-T011.
2. Team B: frontend core auth tasks T012-T016.
3. After Phase 4, split CRUD work: Team A on US3, Team B on US4.
