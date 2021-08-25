import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IParameters } from '../parameters.model';
import { ParametersService } from '../service/parameters.service';

@Component({
  templateUrl: './parameters-delete-dialog.component.html',
})
export class ParametersDeleteDialogComponent {
  parameters?: IParameters;

  constructor(protected parametersService: ParametersService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.parametersService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
