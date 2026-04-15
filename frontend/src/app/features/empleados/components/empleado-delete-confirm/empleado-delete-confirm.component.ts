import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-empleado-delete-confirm',
  standalone: true,
  template: `
    <div class="overlay">
      <section class="dialog">
        <h3>Eliminar empleado</h3>
        <p>Se eliminara {{ clave }}. Esta accion no se puede deshacer.</p>
        <footer>
          <button type="button" (click)="cancel.emit()">Cancelar</button>
          <button type="button" class="danger" (click)="confirm.emit()">Eliminar</button>
        </footer>
      </section>
    </div>
  `,
  styles: [
    `
      .overlay {
        position: fixed;
        inset: 0;
        background: rgba(0, 0, 0, 0.3);
        display: grid;
        place-items: center;
      }
      .dialog {
        background: #fff;
        border-radius: 12px;
        width: min(460px, 92vw);
        padding: 1rem;
      }
      footer {
        display: flex;
        justify-content: flex-end;
        gap: 0.5rem;
        margin-top: 0.6rem;
      }
      .danger {
        background: #a01d1d;
        color: #fff;
      }
    `,
  ],
})
export class EmpleadoDeleteConfirmComponent {
  @Input() clave = '';
  @Output() cancel = new EventEmitter<void>();
  @Output() confirm = new EventEmitter<void>();
}
