import { Component, EventEmitter, Input, Output, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import {
  Departamento,
  DepartamentoCreateRequest,
  DepartamentoUpdateRequest,
} from '../../../../core/models/departamento.model';

@Component({
  selector: 'app-departamento-form-dialog',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './departamento-form-dialog.component.html',
  styleUrl: './departamento-form-dialog.component.scss',
})
export class DepartamentoFormDialogComponent {
  private readonly formBuilder = inject(FormBuilder);

  @Input() value: Departamento | null = null;

  @Output() cancel = new EventEmitter<void>();
  @Output() create = new EventEmitter<DepartamentoCreateRequest>();
  @Output() update = new EventEmitter<{ clave: string; payload: DepartamentoUpdateRequest }>();

  readonly form = this.formBuilder.nonNullable.group({
    clave: ['', [Validators.required]],
    nombre: ['', [Validators.required]],
  });

  ngOnInit(): void {
    if (!this.value) {
      return;
    }

    this.form.patchValue({
      clave: this.value.clave,
      nombre: this.value.nombre,
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
        },
      });
      return;
    }

    this.create.emit({
      clave: raw.clave,
      nombre: raw.nombre,
    });
  }
}
