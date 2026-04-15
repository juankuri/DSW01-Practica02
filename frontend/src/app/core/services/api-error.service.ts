import { Injectable } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { AuthSessionService } from './auth-session.service';

@Injectable({ providedIn: 'root' })
export class ApiErrorService {
  constructor(
    private readonly authSessionService: AuthSessionService,
    private readonly router: Router,
  ) {}

  handle(error: HttpErrorResponse): string {
    if (error.status === 401) {
      this.authSessionService.clear();
      this.router.navigate(['/login']);
      return 'Tu sesion ha expirado. Inicia sesion de nuevo.';
    }

    if (error.status === 400) {
      return 'Solicitud invalida. Revisa los datos e intenta otra vez.';
    }

    if (error.status === 409) {
      return 'Conflicto de datos. Actualiza la vista e intenta nuevamente.';
    }

    if (error.status >= 500) {
      return 'El servicio no esta disponible temporalmente. Intenta mas tarde.';
    }

    return 'No se pudo completar la operacion.';
  }
}
