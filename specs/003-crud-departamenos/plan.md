# Implementation Plan: CRUD de Departamentos

**Branch**: `003-crud-departamenos` | **Date**: 2026-03-10 | **Spec**: `/specs/003-crud-departamenos/spec.md`
**Input**: Feature specification from `/specs/003-crud-departamenos/spec.md`

**Note**: This template is filled in by the `/speckit.plan` command. See `.specify/templates/plan-template.md` for the execution workflow.

## Summary

Documentar e implementar la migración del CRUD de departamentos existente desde `/api/departamentos` hacia `/api/v1/departamentos`, incorporando paginación obligatoria (`page=0`, `size=10`, máximo `50`) con orden estable por `clave` ascendente, documentación OpenAPI dedicada, y cambio de semántica de borrado para responder `404 Not Found` cuando la `clave` no exista. La persistencia PostgreSQL, la normalización por recorte y la seguridad HTTP Basic existentes se conservan y se alinean con nuevas pruebas de contrato, seguridad y persistencia.

## Technical Context

**Language/Version**: Java 17 (MANDATORY)  
**Primary Dependencies**: Spring Boot 3.3.x, Spring Security, Spring Data JPA, Flyway, springdoc-openapi  
**Storage**: PostgreSQL 15 vía Docker Compose, con Flyway para esquema  
**Testing**: JUnit 5, Spring Boot Test, MockMvc, Spring Security Test  
**Target Platform**: Linux server + Docker
**Project Type**: backend web-service  
**Performance Goals**: p95 < 2s para consulta por `clave` y listados paginados bajo carga operativa normal.  
**Constraints**: HTTP Basic obligatorio en endpoints de negocio; rutas públicas bajo `/api/v1/departamentos`; listado paginado con `page >= 0`, `1 <= size <= 50`, default `page=0`, `size=10`; orden fijo por `clave` ascendente; `clave` y `nombre` normalizados por trim y validados en 1..100 caracteres; eliminación de inexistente devuelve `404`.  
**Scale/Scope**: Un solo catálogo administrativo (`Departamento`) con CRUD completo, cambios concentrados en controlador, servicio, pruebas, contrato OpenAPI y documentación operativa, sin cambios de esquema SQL.

## Constitution Check

_GATE: Must pass before Phase 0 research. Re-check after Phase 1 design._

_Required gates for this repository:_

- [x] Confirms Java 17 and Spring Boot 3.x usage.
- [x] Defines HTTP Basic auth for all non-public endpoints.
- [x] Documents local auth defaults (`admin` / `admin123`) and production secret strategy.
- [x] Defines PostgreSQL usage and Docker/Docker Compose execution path.
- [x] Confirms Swagger/OpenAPI updates for any API contract changes.
- [x] Confirms API endpoints are versioned (for example `/api/v1/...`).
- [x] Defines pagination behavior (page, size, defaults, limits) for list endpoints.
- [x] Includes automated test strategy for security, controller, and persistence behavior.

_Pre-Phase 0 Result_: PASS  
_Post-Phase 1 Result_: PASS

## Project Structure

### Documentation (this feature)

```text
specs/003-crud-departamenos/
├── plan.md
├── research.md
├── data-model.md
├── quickstart.md
├── contracts/
│   └── departamentos.openapi.yaml
└── tasks.md
```

### Source Code (repository root)

```text
src/
├── main/
│   ├── java/com/dsw01/practica02/
│   │   ├── config/
│   │   ├── domain/
│   │   ├── repository/
│   │   ├── service/
│   │   └── web/
│   └── resources/
│       ├── application.properties
│       └── db/migration/
└── test/
    └── java/com/dsw01/practica02/
        ├── contract/
        ├── integration/
        └── service/
```

**Structure Decision**: Se mantiene la estructura monolítica actual de Spring Boot. La implementación se concentra en `web/DepartamentoController.java`, `service/DepartamentoService.java`, `repository/DepartamentoRepository.java` y pruebas bajo `contract/`, `integration/` y `service/`, mientras la documentación del feature se conserva dentro de `specs/003-crud-departamenos/`.

## Complexity Tracking

> **Fill ONLY if Constitution Check has violations that must be justified**

| Violation | Why Needed | Simpler Alternative Rejected Because |
| --------- | ---------- | ------------------------------------ |
| None | N/A | N/A |

