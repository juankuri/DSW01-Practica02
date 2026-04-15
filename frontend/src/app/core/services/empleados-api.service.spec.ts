/// <reference types="vitest" />

import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { EmpleadosApiService } from './empleados-api.service';
import { environment } from '../../../environments/environment';
import { PagedResult } from '../models/paged-result.model';
import { Empleado } from '../models/empleado.model';

describe('EmpleadosApiService', () => {
  let service: EmpleadosApiService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [EmpleadosApiService, provideHttpClient(), provideHttpClientTesting()],
    });

    service = TestBed.inject(EmpleadosApiService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('requests paged empleados list', () => {
    service.list(1, 20).subscribe((result: PagedResult<Empleado>) => {
      expect(result.number).toBe(1);
      expect(result.size).toBe(20);
      expect(result.totalElements).toBe(1);
    });

    const req = httpMock.expectOne(`${environment.apiBaseUrl}/api/v1/empleados?page=1&size=20`);
    expect(req.request.method).toBe('GET');
    req.flush({
      content: [
        {
          clave: 'EMP-1',
          nombre: 'Uno',
          direccion: 'Dir',
          telefono: '999',
          version: 0,
          departamentoClave: 'DEP-1',
        },
      ],
      number: 1,
      size: 20,
      totalElements: 1,
      totalPages: 1,
    });
  });

  it('creates empleado', () => {
    service
      .create({
        clave: 'EMP-NEW',
        nombre: 'Nuevo',
        direccion: 'Dir',
        telefono: '777',
      })
      .subscribe();

    const req = httpMock.expectOne(`${environment.apiBaseUrl}/api/v1/empleados`);
    expect(req.request.method).toBe('POST');
    req.flush({});
  });

  it('updates empleado by clave with PUT', () => {
    service
      .update('EMP-1', {
        nombre: 'Editado',
        direccion: 'Dir 2',
        telefono: '888',
        version: 1,
      })
      .subscribe();

    const req = httpMock.expectOne(`${environment.apiBaseUrl}/api/v1/empleados/EMP-1`);
    expect(req.request.method).toBe('PUT');
    req.flush({});
  });

  it('deletes empleado by clave', () => {
    service.delete('EMP-1').subscribe();

    const req = httpMock.expectOne(`${environment.apiBaseUrl}/api/v1/empleados/EMP-1`);
    expect(req.request.method).toBe('DELETE');
    req.flush({});
  });
});
