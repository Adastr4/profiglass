import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICARCLFINI } from '../carclfini.model';
import { CARCLFINIService } from '../service/carclfini.service';

@Component({
  templateUrl: './carclfini-delete-dialog.component.html',
})
export class CARCLFINIDeleteDialogComponent {
  cARCLFINI?: ICARCLFINI;

  constructor(protected cARCLFINIService: CARCLFINIService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cARCLFINIService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
