/// <reference types="vitest" />

import { AuthSessionService } from './auth-session.service';

describe('AuthSessionService', () => {
  let service: AuthSessionService;

  beforeEach(() => {
    sessionStorage.clear();
    service = new AuthSessionService();
  });

  it('stores and reads credentials', () => {
    service.setCredentials('admin', 'admin123');

    expect(service.getUsuario()).toBe('admin');
    expect(service.getPassword()).toBe('admin123');
    expect(service.isAuthenticated()).toBe(true);
  });

  it('clears credentials', () => {
    service.setCredentials('admin', 'admin123');
    service.clear();

    expect(service.getUsuario()).toBeNull();
    expect(service.getPassword()).toBeNull();
    expect(service.isAuthenticated()).toBe(false);
  });

  it('builds basic auth header', () => {
    service.setCredentials('admin', 'admin123');

    expect(service.getAuthorizationHeader()).toBe('Basic YWRtaW46YWRtaW4xMjM=');
  });
});
