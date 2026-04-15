/// <reference types="vitest" />

import { TestBed } from '@angular/core/testing';
import { DepartamentoFormDialogComponent } from './departamento-form-dialog.component';

describe('DepartamentoFormDialogComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DepartamentoFormDialogComponent],
    }).compileComponents();
  });

  it('requires clave and nombre', () => {
    const fixture = TestBed.createComponent(DepartamentoFormDialogComponent);
    fixture.detectChanges();

    const component = fixture.componentInstance;
    expect(component.form.valid).toBe(false);

    component.form.setValue({
      clave: 'DEP-1',
      nombre: 'Finanzas',
    });

    expect(component.form.valid).toBe(true);
  });
});
