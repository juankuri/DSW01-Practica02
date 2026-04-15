/// <reference types="vitest" />

import { describe, it } from 'vitest';

// E2E contract for login/auth flow (runner integration pending).
// Suggested runner: Playwright.

describe('Login and auth flow', () => {
  it('redirects to /login when opening protected route without credentials', async () => {
    // 1) open /empleados
    // 2) expect current URL to contain /login
  });

  it('allows access to /empleados after valid login', async () => {
    // 1) open /login
    // 2) fill usuario/password
    // 3) submit and expect URL to contain /empleados
  });

  it('clears session and returns to /login on logout', async () => {
    // 1) login successfully
    // 2) click Salir
    // 3) expect URL to contain /login
  });
});
