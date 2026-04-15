import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';
import { LoginComponent } from './features/auth/login/login.component';
import { EmpleadosListComponent } from './features/empleados/list/empleados-list.component';
import { DepartamentosListComponent } from './features/departamentos/list/departamentos-list.component';
import { ProtectedShellComponent } from './shared/layouts/protected-shell.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  {
    path: '',
    component: ProtectedShellComponent,
    canActivate: [authGuard],
    children: [
      { path: 'empleados', component: EmpleadosListComponent },
      { path: 'departamentos', component: DepartamentosListComponent },
      { path: '', pathMatch: 'full', redirectTo: 'empleados' },
    ],
  },
  { path: '**', redirectTo: '' },
];
