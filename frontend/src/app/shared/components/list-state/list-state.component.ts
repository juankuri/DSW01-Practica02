import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-list-state',
  standalone: true,
  template: `
    @if (loading) {
      <p class="state info">Cargando...</p>
    } @else if (error) {
      <p class="state error">{{ error }}</p>
    } @else if (empty) {
      <p class="state neutral">No hay registros para mostrar.</p>
    }
  `,
  styles: [
    `
      .state {
        margin: 0.75rem 0;
        padding: 0.7rem 0.85rem;
        border-radius: 12px;
        border: 1px solid transparent;
      }

      .info {
        color: #225650;
        border-color: #c6ddd7;
        background: #ebf6f3;
      }

      .neutral {
        color: #46605b;
        border-color: #d6e4e0;
        background: #f5faf8;
      }

      .error {
        color: #a02737;
        border-color: #edc8cf;
        background: #fff3f5;
      }
    `,
  ],
})
export class ListStateComponent {
  @Input() loading = false;
  @Input() empty = false;
  @Input() error = '';
}
