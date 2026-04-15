# Research: Angular Frontend - Login y CRUD de Empleados y Departamentos

## Decision 1: API base URL and versioning

**Decision**: Use versioned endpoints under `/api/v1/...` for all frontend calls. Configure the base URL in Angular environment files.
**Rationale**: The project constitution requires versioned routes and externalized API base URL. Aligning with `/api/v1` keeps contracts stable for clients.
**Alternatives considered**:

- Use `/api/empleados` and `/api/departamentos` as in the older empleados contract. Rejected because it conflicts with constitution and newer departments contract.

## Decision 2: Authentication flow

**Decision**: Use HTTP Basic for every API call, with the login form collecting `usuario` + password and storing them in `sessionStorage` for the active tab.
**Rationale**: Matches existing backend security model (HTTP Basic) and remains compatible with local defaults (`admin` / `admin123`) and production credentials.
**Alternatives considered**:

- Use a token-based session. Rejected because backend does not expose a token endpoint and would require new backend work.

## Decision 3: Employee and department list fields

**Decision**: The frontend empleados table will show `clave`, `nombre`, `direccion`, `telefono`, `departamento`; departments table will show `clave`, `nombre`.
**Rationale**: Matches current backend DTOs and avoids contract drift.
**Alternatives considered**:

- Show `email` in empleados list. Rejected because current backend DTO does not expose `email` and would force backend business changes.

## Decision 4: Form presentation

**Decision**: Create and edit use modal dialogs over both empleados and departamentos lists; delete uses a confirm dialog.
**Rationale**: Maintains context, shortens the flow, and aligns with the requested UX.
**Alternatives considered**:

- Separate routes for create/edit. Rejected to keep the UI on a single list page.

## Decision 5: Pagination behavior

**Decision**: Use backend pagination with query params `page` and `size` for empleados and departamentos. Provide size options 5/10/20 and respect backend max size (50) when present.
**Rationale**: Aligns with constitution and existing pagination contract for departments.
**Alternatives considered**:

- Client-side pagination. Rejected to avoid loading full datasets and to align with API requirements.

## Decision 6: Error handling

**Decision**: Standardize error messages for login, list, and CRUD actions. Redirect on 401 and show a non-technical error for 400/409/500.
**Rationale**: Improves UX and matches the edge cases in the spec.
**Alternatives considered**:

- Expose raw backend error content. Rejected to avoid leaking implementation details.

## Decision 7: Update method

**Decision**: Use `PUT` as the only update method in this feature for empleados and departamentos.
**Rationale**: Current backend supports `PUT`; deterministic behavior removes ambiguity and test branching.
**Alternatives considered**:

- Dynamic `PUT`/`PATCH` selection. Rejected due to unnecessary runtime negotiation complexity.

## Decision 8: Backend change policy for this feature

**Decision**: Allow backend configuration-only changes (CORS and route compatibility/version aliases) while avoiding business-logic behavior changes.
**Rationale**: Resolves contradiction between frontend needs and constitution constraints.
**Alternatives considered**:

- Zero backend changes at all. Rejected because browser CORS policy and versioned routing would block compliant frontend operation.
