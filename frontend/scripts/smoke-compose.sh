#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)"

cd "$ROOT_DIR"

echo "[smoke] Building and starting compose services..."
docker compose up -d --build

echo "[smoke] Waiting for backend health endpoint..."
backend_ok=0
for _ in {1..30}; do
  status="$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8080/actuator/health || true)"
  if [[ "$status" == "200" || "$status" == "401" ]]; then
    backend_ok=1
    break
  fi
  sleep 2
done

if [[ "$backend_ok" -ne 1 ]]; then
  echo "[smoke] ERROR: backend did not become reachable on http://localhost:8080/actuator/health"
  exit 1
fi

echo "[smoke] Verifying frontend is reachable..."
curl -fsS http://localhost:8081 >/dev/null

echo "[smoke] OK: backend and frontend endpoints reachable."
