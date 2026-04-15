import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-pagination',
  standalone: true,
  templateUrl: './pagination.component.html',
  styleUrl: './pagination.component.scss',
})
export class PaginationComponent {
  @Input() page = 0;
  @Input() size = 10;
  @Input() totalPages = 0;

  @Output() pageChange = new EventEmitter<number>();
  @Output() sizeChange = new EventEmitter<number>();

  readonly sizes = [5, 10, 20];

  prev(): void {
    if (this.page > 0) {
      this.pageChange.emit(this.page - 1);
    }
  }

  next(): void {
    if (this.page + 1 < this.totalPages) {
      this.pageChange.emit(this.page + 1);
    }
  }

  onSizeChange(value: string): void {
    this.sizeChange.emit(Number(value));
  }
}
