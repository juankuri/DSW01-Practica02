# Quickstart: Angular Frontend - Login y CRUD de Empleados y Departamentos

## Prerequisites

- Node.js 20.x
- npm 10.x
- Docker + Docker Compose

## Run backend + database

```bash
cd /home/kuri/uni/sexto/despliegue/DSW01-Practica02

docker compose up -d --build
```

## Run frontend (dev)

```bash
cd /home/kuri/uni/sexto/despliegue/DSW01-Practica02/frontend
npm install
npm run start
```

- App: http://localhost:4200
- API: http://localhost:8080 (configured in `environment.ts`)

## Build frontend container

```bash
cd /home/kuri/uni/sexto/despliegue/DSW01-Practica02/frontend
npm run build

docker build -t dsw01-frontend .
```

## Run full stack

```bash
cd /home/kuri/uni/sexto/despliegue/DSW01-Practica02

docker compose up -d --build
```

If local port 5432 is already in use, run compose with an alternate host port:

```bash
DB_PORT=5433 docker compose up -d --build
```

## Scope validation checklist

- Login accepts `usuario` + password and applies HTTP Basic on all protected calls.
- Empleados list + CRUD works with modal create/edit and confirm delete.
- Departamentos list + CRUD works with modal create/edit and confirm delete.
- Both lists show pagination controls and metadata (`page`, `size`, `totalElements`).

## Validation Evidence (2026-03-26)

- Frontend unit tests (Node 20.20.1):
    - Command: `source ~/.nvm/nvm.sh && nvm use 20.20.1 && npm run test -- --watch=false`
    - Result: 8 test files passed, 19 tests passed.
- Frontend E2E contract suite (Vitest wrappers):
    - Command: `source ~/.nvm/nvm.sh && nvm use 20.20.1 && npx vitest run e2e/*.spec.ts`
    - Result: 4 test files passed, 9 tests passed.
- Backend test suite:
    - Command: `mvn -q test`
    - Result: passed.
- Compose smoke:
    - Command: `DB_PORT=5433 ./frontend/scripts/smoke-compose.sh`
    - Result: backend and frontend reachable.

## SC Timing Goals and Latest Measurements

- Goal: login page load under 2s on localhost.
    - Latest measurement: frontend build + serve smoke endpoint available; UI load timing should be measured in browser perf panel on next QA pass.
- Goal: UI updates under 300ms after successful API mutation.
    - Latest measurement: contract and unit tests pass; mutation latency measurement pending browser/network profiling in integrated QA.
- Current automated timing references:
    - Frontend unit test duration: ~3.91s total suite.
    - Frontend E2E contract suite duration: ~0.63s total suite.
