import { Injectable } from '@angular/core';

const STORAGE_USER_KEY = 'auth.usuario';
const STORAGE_PASSWORD_KEY = 'auth.password';

@Injectable({ providedIn: 'root' })
export class AuthSessionService {
  setCredentials(usuario: string, password: string): void {
    sessionStorage.setItem(STORAGE_USER_KEY, usuario);
    sessionStorage.setItem(STORAGE_PASSWORD_KEY, password);
  }

  clear(): void {
    sessionStorage.removeItem(STORAGE_USER_KEY);
    sessionStorage.removeItem(STORAGE_PASSWORD_KEY);
  }

  getUsuario(): string | null {
    return sessionStorage.getItem(STORAGE_USER_KEY);
  }

  getPassword(): string | null {
    return sessionStorage.getItem(STORAGE_PASSWORD_KEY);
  }

  isAuthenticated(): boolean {
    return !!this.getUsuario() && !!this.getPassword();
  }

  getAuthorizationHeader(): string | null {
    const usuario = this.getUsuario();
    const password = this.getPassword();

    if (!usuario || !password) {
      return null;
    }

    return `Basic ${btoa(`${usuario}:${password}`)}`;
  }
}
