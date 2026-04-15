import { Component, inject } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import {
  Empleado,
  EmpleadoCreateRequest,
  EmpleadoUpdateRequest,
} from '../../../core/models/empleado.model';
import { EmpleadosApiService } from '../../../core/services/empleados-api.service';
import { ApiErrorService } from '../../../core/services/api-error.service';
import { PaginationComponent } from '../../../shared/components/pagination/pagination.component';
import { ListStateComponent } from '../../../shared/components/list-state/list-state.component';
import { EmpleadoFormDialogComponent } from '../components/empleado-form-dialog/empleado-form-dialog.component';
import { EmpleadoDeleteConfirmComponent } from '../components/empleado-delete-confirm/empleado-delete-confirm.component';
import { PagedResult } from '../../../core/models/paged-result.model';

@Component({
  selector: 'app-empleados-list',
  standalone: true,
  imports: [
    PaginationComponent,
    ListStateComponent,
    EmpleadoFormDialogComponent,
    EmpleadoDeleteConfirmComponent,
  ],
  templateUrl: './empleados-list.component.html',
  styleUrl: './empleados-list.component.scss',
})
export class EmpleadosListComponent {
  private readonly empleadosApi = inject(EmpleadosApiService);
  private readonly apiErrorService = inject(ApiErrorService);

  rows: Empleado[] = [];
  page = 0;
  size = 10;
  totalElements = 0;
  totalPages = 0;
  loading = false;
  error = '';
  showFormDialog = false;
  selectedEmpleado: Empleado | null = null;
  deleteTarget: Empleado | null = null;

  constructor() {
    this.fetchData();
  }

  onPageChange(nextPage: number): void {
    this.page = nextPage;
    this.fetchData();
  }

  onSizeChange(nextSize: number): void {
    this.size = nextSize;
    this.page = 0;
    this.fetchData();
  }

  openCreateDialog(): void {
    this.selectedEmpleado = null;
    this.showFormDialog = true;
  }

  openEditDialog(item: Empleado): void {
    this.selectedEmpleado = item;
    this.showFormDialog = true;
  }

  closeFormDialog(): void {
    this.showFormDialog = false;
    this.selectedEmpleado = null;
  }

  requestDelete(item: Empleado): void {
    this.deleteTarget = item;
  }

  cancelDelete(): void {
    this.deleteTarget = null;
  }

  createEmpleado(payload: EmpleadoCreateRequest): void {
    this.empleadosApi.create(payload).subscribe({
      next: () => {
        this.closeFormDialog();
        this.fetchData();
      },
      error: (httpError: HttpErrorResponse) => {
        this.error = this.apiErrorService.handle(httpError);
      },
    });
  }

  updateEmpleado(event: { clave: string; payload: EmpleadoUpdateRequest }): void {
    this.empleadosApi.update(event.clave, event.payload).subscribe({
      next: () => {
        this.closeFormDialog();
        this.fetchData();
      },
      error: (httpError: HttpErrorResponse) => {
        this.error = this.apiErrorService.handle(httpError);
      },
    });
  }

  confirmDelete(): void {
    if (!this.deleteTarget) {
      return;
    }

    this.empleadosApi.delete(this.deleteTarget.clave).subscribe({
      next: () => {
        this.deleteTarget = null;
        this.fetchData();
      },
      error: (httpError: HttpErrorResponse) => {
        this.error = this.apiErrorService.handle(httpError);
        this.deleteTarget = null;
      },
    });
  }

  private fetchData(): void {
    this.loading = true;
    this.error = '';

    this.empleadosApi.list(this.page, this.size).subscribe({
      next: (result: PagedResult<Empleado>) => {
        this.rows = result.content;
        this.page = result.number;
        this.size = result.size;
        this.totalElements = result.totalElements;
        this.totalPages = result.totalPages;
        this.loading = false;
      },
      error: (httpError: HttpErrorResponse) => {
        this.error = this.apiErrorService.handle(httpError);
        this.loading = false;
      },
    });
  }
}
