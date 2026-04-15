# Implementation Plan: Angular Frontend - Login y CRUD de Empleados y Departamentos

**Branch**: `004-frontend-empleados-login` | **Date**: 2026-03-11 | **Spec**: [specs/004-frontend-empleados-login/spec.md](specs/004-frontend-empleados-login/spec.md)
**Input**: Feature specification from `/specs/004-frontend-empleados-login/spec.md`

**Note**: This template is filled in by the `/speckit.plan` command. See `.specify/templates/plan-template.md` for the execution workflow.

## Summary

Build an Angular 21 frontend in `frontend/` that provides login (username/email + password) and
full CRUD UI for empleados and departamentos. The frontend reuses existing HTTP Basic endpoints,
stores credentials in `sessionStorage`, uses modal dialogs for create/edit and confirmation dialogs
for delete, and renders paginated tables for both modules. Updates are sent via `PUT` in this phase.
Backend business endpoints stay functionally equivalent, but backend configuration updates are allowed
for CORS and route-versioning compatibility aliases.

## Technical Context

**Language/Version**: Java 17 (MANDATORY) | Angular 21 + TypeScript (frontend)  
**Primary Dependencies**: Spring Boot 3.x, Spring Security, Spring Data JPA, springdoc-openapi, Angular 21  
**Storage**: PostgreSQL (Dockerized in local/CI)  
**Testing**: JUnit 5, Spring Boot Test, MockMvc; Angular unit tests + E2E flow tests  
**Target Platform**: Linux server + Docker (backend + frontend served via nginx)  
**Project Type**: monorepo web-service (Spring Boot backend + Angular 21 frontend)  
**Performance Goals**: Initial login page load < 2s on localhost; UI updates within 300ms after API success  
**Constraints**: HTTP Basic only; API base URL via environment files; no hardcoded URLs; update uses `PUT`; backend config-only changes allowed for CORS and endpoint compatibility  
**Scale/Scope**: Admin-only UI; expected small operator count (< 50 concurrent users)

## Constitution Check

_GATE: Must pass before Phase 0 research. Re-check after Phase 1 design._

_Required gates for this repository:_

- [x] Confirms Java 17 and Spring Boot 3.x usage (backend unchanged).
- [x] Defines HTTP Basic auth for all non-public endpoints (frontend sends Basic on every call).
- [x] Documents local auth defaults (`admin` / `admin123`) and production secret strategy (backend defaults remain; prod via env-managed secrets; frontend only collects credentials).
- [x] Defines PostgreSQL usage and Docker/Docker Compose execution path (existing compose remains; frontend added).
- [x] Confirms Swagger/OpenAPI updates for any API contract changes (including versioned aliases when added).
- [x] Confirms API endpoints are versioned (frontend targets `/api/v1/...` with compatibility path as needed).
- [x] Defines pagination behavior (page, size, defaults, limits) for list endpoints in empleados and departamentos (size options 5/10/20; respects backend limits).
- [x] Includes automated test strategy for security, list rendering, and CRUD behavior (frontend unit + E2E; backend regression tests for config/contract).
- [x] Confirms Angular 21 frontend resides in `frontend/` and uses environment files for API base URL.
- [x] Confirms backend CORS configuration permits the frontend origin (config change accepted as non-functional backend change).
- [x] Confirms `frontend/Dockerfile` (multi-stage) and `docker-compose.yml` frontend service are updated.

Post-design check: PASS (see research, data model, contracts, quickstart).

## Project Structure

### Documentation (this feature)

```text
specs/004-frontend-empleados-login/
├── plan.md
├── research.md
├── data-model.md
├── quickstart.md
├── contracts/
└── tasks.md
```

### Source Code (repository root)

```text
src/                          # Spring Boot source (existing)
├── main/java/
├── main/resources/
└── test/java/

frontend/                     # Angular 21 project (to be created)
├── src/
│   ├── app/
│   │   ├── core/
│   │   ├── shared/
│   │   └── features/
│   └── environments/
├── angular.json
├── package.json
├── Dockerfile
└── nginx.conf
```

**Structure Decision**: Monorepo with Spring Boot at repository root and Angular 21 in `frontend/`; frontend includes modules for auth, empleados, and departamentos.

## Complexity Tracking

No constitution violations.
