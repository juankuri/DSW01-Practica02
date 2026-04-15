/// <reference types="vitest" />

import { TestBed } from '@angular/core/testing';
import { EmpleadoFormDialogComponent } from './empleado-form-dialog.component';
import { DepartamentosApiService } from '../../../../core/services/departamentos-api.service';
import { of } from 'rxjs';

describe('EmpleadoFormDialogComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EmpleadoFormDialogComponent],
      providers: [
        {
          provide: DepartamentosApiService,
          useValue: {
            list: () => of({ content: [], number: 0, size: 100, totalElements: 0, totalPages: 0 }),
          },
        },
      ],
    }).compileComponents();
  });

  it('requires mandatory fields', () => {
    const fixture = TestBed.createComponent(EmpleadoFormDialogComponent);
    fixture.detectChanges();

    const component = fixture.componentInstance;
    expect(component.form.valid).toBe(false);

    component.form.setValue({
      clave: 'EMP-1',
      nombre: 'Empleado',
      direccion: 'Direccion',
      telefono: '999',
      departamentoClave: '',
    });

    expect(component.form.valid).toBe(true);
  });
});
