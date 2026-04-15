import { Component, inject } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import {
  Departamento,
  DepartamentoCreateRequest,
  DepartamentoUpdateRequest,
} from '../../../core/models/departamento.model';
import { PagedResult } from '../../../core/models/paged-result.model';
import { DepartamentosApiService } from '../../../core/services/departamentos-api.service';
import { ApiErrorService } from '../../../core/services/api-error.service';
import { ListStateComponent } from '../../../shared/components/list-state/list-state.component';
import { PaginationComponent } from '../../../shared/components/pagination/pagination.component';
import { DepartamentoFormDialogComponent } from '../components/departamento-form-dialog/departamento-form-dialog.component';
import { DepartamentoDeleteConfirmComponent } from '../components/departamento-delete-confirm/departamento-delete-confirm.component';

@Component({
  selector: 'app-departamentos-list',
  standalone: true,
  imports: [
    PaginationComponent,
    ListStateComponent,
    DepartamentoFormDialogComponent,
    DepartamentoDeleteConfirmComponent,
  ],
  templateUrl: './departamentos-list.component.html',
  styleUrl: './departamentos-list.component.scss',
})
export class DepartamentosListComponent {
  private readonly departamentosApi = inject(DepartamentosApiService);
  private readonly apiErrorService = inject(ApiErrorService);

  rows: Departamento[] = [];
  page = 0;
  size = 10;
  totalElements = 0;
  totalPages = 0;
  loading = false;
  error = '';
  showFormDialog = false;
  selectedDepartamento: Departamento | null = null;
  deleteTarget: Departamento | null = null;

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
    this.selectedDepartamento = null;
    this.showFormDialog = true;
  }

  openEditDialog(item: Departamento): void {
    this.selectedDepartamento = item;
    this.showFormDialog = true;
  }

  closeFormDialog(): void {
    this.showFormDialog = false;
    this.selectedDepartamento = null;
  }

  requestDelete(item: Departamento): void {
    this.deleteTarget = item;
  }

  cancelDelete(): void {
    this.deleteTarget = null;
  }

  createDepartamento(payload: DepartamentoCreateRequest): void {
    this.departamentosApi.create(payload).subscribe({
      next: () => {
        this.closeFormDialog();
        this.fetchData();
      },
      error: (httpError: HttpErrorResponse) => {
        this.error = this.apiErrorService.handle(httpError);
      },
    });
  }

  updateDepartamento(event: { clave: string; payload: DepartamentoUpdateRequest }): void {
    this.departamentosApi.update(event.clave, event.payload).subscribe({
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

    this.departamentosApi.delete(this.deleteTarget.clave).subscribe({
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

    this.departamentosApi.list(this.page, this.size).subscribe({
      next: (result: PagedResult<Departamento>) => {
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
