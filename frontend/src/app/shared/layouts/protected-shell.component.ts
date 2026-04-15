import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { TopbarComponent } from '../components/topbar/topbar.component';

@Component({
  selector: 'app-protected-shell',
  standalone: true,
  imports: [RouterOutlet, TopbarComponent],
  template: `
    <app-topbar></app-topbar>
    <main class="shell-content">
      <section class="shell-container">
        <router-outlet></router-outlet>
      </section>
    </main>
  `,
  styles: [
    `
      .shell-content {
        padding: 0.75rem 1rem 1.4rem;
      }

      .shell-container {
        width: min(1120px, 100%);
        margin: 0 auto;
        animation: rise-in 220ms ease;
      }

      @media (max-width: 860px) {
        .shell-content {
          padding: 0 0.7rem 1rem;
        }
      }
    `,
  ],
})
export class ProtectedShellComponent {}
