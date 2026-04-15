# Frontend Routing Contract

## Public route

- `/login`

## Protected routes

- `/empleados`
- `/departamentos`

## Guard rules

- Missing session credentials -> redirect to `/login`.
- HTTP 401 in any protected API call -> clear session + redirect to `/login`.

## Navigation outcomes

- Successful login -> `/empleados`.
- Logout -> `/login`.
- Create/edit/delete success -> stay on current module and refresh current table page.
