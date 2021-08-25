import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICLLEGA } from '../cllega.model';
import { CLLEGAService } from '../service/cllega.service';

@Component({
  templateUrl: './cllega-delete-dialog.component.html',
})
export class CLLEGADeleteDialogComponent {
  cLLEGA?: ICLLEGA;

  constructor(protected cLLEGAService: CLLEGAService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cLLEGAService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
