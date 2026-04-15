import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Empleado, EmpleadoCreateRequest, EmpleadoUpdateRequest } from '../models/empleado.model';
import { PagedResult } from '../models/paged-result.model';

@Injectable({ providedIn: 'root' })
export class EmpleadosApiService {
  private readonly http = inject(HttpClient);
  private readonly basePath = `${environment.apiBaseUrl}/api/v1/empleados`;

  list(page: number, size: number): Observable<PagedResult<Empleado>> {
    const params = new HttpParams().set('page', page).set('size', size);
    return this.http.get<PagedResult<Empleado>>(this.basePath, { params });
  }

  create(payload: EmpleadoCreateRequest): Observable<Empleado> {
    return this.http.post<Empleado>(this.basePath, payload);
  }

  update(clave: string, payload: EmpleadoUpdateRequest): Observable<Empleado> {
    return this.http.put<Empleado>(`${this.basePath}/${clave}`, payload);
  }

  delete(clave: string): Observable<void> {
    return this.http.delete<void>(`${this.basePath}/${clave}`);
  }
}
