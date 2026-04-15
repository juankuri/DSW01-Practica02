import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import {
  Departamento,
  DepartamentoCreateRequest,
  DepartamentoUpdateRequest,
} from '../models/departamento.model';
import { PagedResult } from '../models/paged-result.model';

@Injectable({ providedIn: 'root' })
export class DepartamentosApiService {
  private readonly http = inject(HttpClient);
  private readonly basePath = `${environment.apiBaseUrl}/api/v1/departamentos`;

  list(page: number, size: number): Observable<PagedResult<Departamento>> {
    const params = new HttpParams().set('page', page).set('size', size);
    return this.http.get<PagedResult<Departamento>>(this.basePath, { params });
  }

  create(payload: DepartamentoCreateRequest): Observable<Departamento> {
    return this.http.post<Departamento>(this.basePath, payload);
  }

  update(clave: string, payload: DepartamentoUpdateRequest): Observable<Departamento> {
    return this.http.put<Departamento>(`${this.basePath}/${clave}`, payload);
  }

  delete(clave: string): Observable<void> {
    return this.http.delete<void>(`${this.basePath}/${clave}`);
  }
}
