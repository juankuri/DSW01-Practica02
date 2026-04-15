import { Component, EventEmitter, Input, Output, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import {
  Empleado,
  EmpleadoCreateRequest,
  EmpleadoUpdateRequest,
} from '../../../../core/models/empleado.model';
import { DepartamentosApiService } from '../../../../core/services/departamentos-api.service';
import { Departamento } from '../../../../core/models/departamento.model';
import { PagedResult } from '../../../../core/models/paged-result.model';

@Component({
  selector: 'app-empleado-form-dialog',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './empleado-form-dialog.component.html',
  styleUrl: './empleado-form-dialog.component.scss',
})
export class EmpleadoFormDialogComponent {
  private readonly formBuilder = inject(FormBuilder);
  private readonly departamentosApi = inject(DepartamentosApiService);

  @Input() value: Empleado | null = null;

  @Output() cancel = new EventEmitter<void>();
  @Output() create = new EventEmitter<EmpleadoCreateRequest>();
  @Output() update = new EventEmitter<{ clave: string; payload: EmpleadoUpdateRequest }>();

  departamentos: Departamento[] = [];

  readonly form = this.formBuilder.nonNullable.group({
    clave: ['', [Validators.required]],
    nombre: ['', [Validators.required]],
    direccion: ['', [Validators.required]],
    telefono: ['', [Validators.required]],
    departamentoClave: [''],
  });

  ngOnInit(): void {
    this.departamentosApi.list(0, 100).subscribe({
      next: (result: PagedResult<Departamento>) => {
        this.departamentos = result.content;
      },
    });

    if (!this.value) {
      return;
    }

    this.form.patchValue({
      clave: this.value.clave,
      nombre: this.value.nombre,
      direccion: this.value.direccion,
      telefono: this.value.telefono,
      departamentoClave: this.value.departamentoClave ?? '',
    });
    this.form.controls.clave.disable();
  }

  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const raw = this.form.getRawValue();

    if (this.value) {
      this.update.emit({
        clave: this.value.clave,
        payload: {
          nombre: raw.nombre,
          direccion: raw.direccion,
          telefono: raw.telefono,
          departamentoClave: raw.departamentoClave || null,
          version: this.value.version,
        },
      });
      return;
    }

    this.create.emit({
      clave: raw.clave,
      nombre: raw.nombre,
      direccion: raw.direccion,
      telefono: raw.telefono,
      departamentoClave: raw.departamentoClave || null,
    });
  }
}
