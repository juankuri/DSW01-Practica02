# Data Model: Angular Frontend - Login y CRUD de Empleados y Departamentos

## Entity: AuthSession

- Purpose: Store current credentials for HTTP Basic in the browser session.
- Fields:
    - `usuario`: string (required)
    - `password`: string (required)
- Storage: `sessionStorage` only; cleared on logout or 401.

## Entity: EmpleadoRow

- Purpose: Table row representation in the employees list.
- Fields:
    - `clave`: string (required)
    - `nombre`: string (required)
    - `direccion`: string (required)
    - `telefono`: string (required)
    - `departamento`: string (required, department key)
    - `version`: integer (required for update)

## Entity: EmpleadoForm

- Purpose: Form model for create/edit dialogs.
- Fields:
    - `clave`: string (required, read-only on edit)
    - `nombre`: string (required)
    - `direccion`: string (required)
    - `telefono`: string (required)
    - `departamento`: string (required, department key)
    - `version`: integer (required on edit)
- Validation:
    - `clave`: 1..100, trimmed, non-empty
    - `nombre`: 1..100, trimmed, non-empty
    - `direccion`: 1..100, trimmed, non-empty
    - `telefono`: 1..100, trimmed, non-empty
    - `departamento`: 1..100, trimmed, non-empty

## Entity: DepartamentoRow

- Purpose: Table row representation in the departamentos list.
- Fields:
    - `clave`: string (required)
    - `nombre`: string (required)

## Entity: DepartamentoForm

- Purpose: Form model for create/edit dialogs of departamentos.
- Fields:
    - `clave`: string (required, read-only on edit)
    - `nombre`: string (required)
- Validation:
    - `clave`: 1..100, trimmed, non-empty
    - `nombre`: 1..100, trimmed, non-empty

## Entity: DepartamentoOption

- Purpose: Dropdown option for empleado department selector.
- Fields:
    - `clave`: string (required)
    - `nombre`: string (required)

## Entity: PageMeta

- Purpose: Pagination metadata for table.
- Fields:
    - `page`: integer (0-based)
    - `size`: integer
    - `totalElements`: integer
    - `totalPages`: integer

## API Mapping Notes

- `departamento` in the UI maps to `departamentoClave` in backend payloads.
- Empleado updates include `version` for optimistic concurrency.
