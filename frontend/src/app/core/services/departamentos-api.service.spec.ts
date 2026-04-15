/// <reference types="vitest" />

import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { DepartamentosApiService } from './departamentos-api.service';
import { environment } from '../../../environments/environment';
import { PagedResult } from '../models/paged-result.model';
import { Departamento } from '../models/departamento.model';

describe('DepartamentosApiService', () => {
  let service: DepartamentosApiService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [DepartamentosApiService, provideHttpClient(), provideHttpClientTesting()],
    });

    service = TestBed.inject(DepartamentosApiService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('requests paged departamentos list', () => {
    service.list(0, 10).subscribe((result: PagedResult<Departamento>) => {
      expect(result.number).toBe(0);
      expect(result.size).toBe(10);
      expect(result.totalElements).toBe(1);
    });

    const req = httpMock.expectOne(`${environment.apiBaseUrl}/api/v1/departamentos?page=0&size=10`);
    expect(req.request.method).toBe('GET');
    req.flush({
      content: [{ clave: 'DEP-1', nombre: 'Finanzas' }],
      number: 0,
      size: 10,
      totalElements: 1,
      totalPages: 1,
    });
  });

  it('creates departamento', () => {
    service
      .create({
        clave: 'DEP-NEW',
        nombre: 'Nuevo',
      })
      .subscribe();

    const req = httpMock.expectOne(`${environment.apiBaseUrl}/api/v1/departamentos`);
    expect(req.request.method).toBe('POST');
    req.flush({});
  });

  it('updates departamento by clave with PUT', () => {
    service
      .update('DEP-1', {
        nombre: 'Editado',
      })
      .subscribe();

    const req = httpMock.expectOne(`${environment.apiBaseUrl}/api/v1/departamentos/DEP-1`);
    expect(req.request.method).toBe('PUT');
    req.flush({});
  });

  it('deletes departamento by clave', () => {
    service.delete('DEP-1').subscribe();

    const req = httpMock.expectOne(`${environment.apiBaseUrl}/api/v1/departamentos/DEP-1`);
    expect(req.request.method).toBe('DELETE');
    req.flush({});
  });
});
