# Frontend API Contract (Consumption)

## Base URL

- Configured in `frontend/src/environments/environment.ts` and `environment.prod.ts`.
- Default dev URL: `http://localhost:8080`.

## Auth

- HTTP Basic on every request.
- Header: `Authorization: Basic <base64(usuario:password)>`.

## Endpoints Used

### List employees

- `GET /api/v1/empleados?page={page}&size={size}`
- Response: paginated list with fields `content`, `number`, `size`, `totalElements`, `totalPages`.

### Create employee

- `POST /api/v1/empleados`
- Body:
    ```json
    {
        "clave": "EMP-001",
        "nombre": "Juan Perez",
        "direccion": "Av. Principal 123",
        "telefono": "+51999999999",
        "departamentoClave": "DEP-01"
    }
    ```

### Update employee

- `PUT /api/v1/empleados/{clave}`
- Body:
    ```json
    {
        "nombre": "Juan Perez",
        "direccion": "Av. Principal 999",
        "telefono": "+51988888888",
        "departamentoClave": "DEP-01",
        "version": 1
    }
    ```

### Delete employee

- `DELETE /api/v1/empleados/{clave}`

### List departments

- `GET /api/v1/departamentos?page={page}&size={size}`
- Response: paginated list with fields `content`, `number`, `size`, `totalElements`, `totalPages`.

### Create department

- `POST /api/v1/departamentos`
- Body:
    ```json
    {
        "clave": "DEP-01",
        "nombre": "Finanzas"
    }
    ```

### Update department

- `PUT /api/v1/departamentos/{clave}`
- Body:
    ```json
    {
        "nombre": "Finanzas y Control"
    }
    ```

### Delete department

- `DELETE /api/v1/departamentos/{clave}`

## Error Handling

- `401`: redirect to `/login` and clear session storage.
- `400/409`: show validation or conflict message.
- `5xx`: show generic service error.

## Notes

- If backend still exposes non-versioned routes, implementation includes compatibility aliases and OpenAPI sync in the same change.
- Backend config changes for CORS are in-scope for this feature even without business logic changes.
