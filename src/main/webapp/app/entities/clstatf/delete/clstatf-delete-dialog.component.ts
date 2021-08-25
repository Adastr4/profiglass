import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICLSTATF } from '../clstatf.model';
import { CLSTATFService } from '../service/clstatf.service';

@Component({
  templateUrl: './clstatf-delete-dialog.component.html',
})
export class CLSTATFDeleteDialogComponent {
  cLSTATF?: ICLSTATF;

  constructor(protected cLSTATFService: CLSTATFService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cLSTATFService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
