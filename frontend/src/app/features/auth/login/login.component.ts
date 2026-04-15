import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthSessionService } from '../../../core/services/auth-session.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {
  private readonly formBuilder = inject(FormBuilder);
  private readonly authSessionService = inject(AuthSessionService);
  private readonly router = inject(Router);

  readonly loginForm = this.formBuilder.nonNullable.group({
    usuario: ['', [Validators.required]],
    password: ['', [Validators.required]],
  });

  errorMessage = '';

  onSubmit(): void {
    if (this.loginForm.invalid) {
      this.loginForm.markAllAsTouched();
      return;
    }

    const { usuario, password } = this.loginForm.getRawValue();
    this.authSessionService.setCredentials(usuario, password);
    this.errorMessage = '';
    this.router.navigate(['/empleados']);
  }
}
