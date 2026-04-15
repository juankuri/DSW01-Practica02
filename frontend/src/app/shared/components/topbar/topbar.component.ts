import { Component, inject } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { AuthSessionService } from '../../../core/services/auth-session.service';

@Component({
  selector: 'app-topbar',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './topbar.component.html',
  styleUrl: './topbar.component.scss',
})
export class TopbarComponent {
  private readonly authSessionService = inject(AuthSessionService);
  private readonly router = inject(Router);

  logout(): void {
    this.authSessionService.clear();
    this.router.navigate(['/login']);
  }
}
