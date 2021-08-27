import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ICARCLFINI, CARCLFINI } from '../carclfini.model';
import { CARCLFINIService } from '../service/carclfini.service';

@Component({
  selector: 'jhi-carclfini-update',
  templateUrl: './carclfini-update.component.html',
})
export class CARCLFINIUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    classe: [null, [Validators.required]],
  });

  constructor(protected cARCLFINIService: CARCLFINIService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cARCLFINI }) => {
      this.updateForm(cARCLFINI);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cARCLFINI = this.createFromForm();
    if (cARCLFINI.id !== undefined) {
      this.subscribeToSaveResponse(this.cARCLFINIService.update(cARCLFINI));
    } else {
      this.subscribeToSaveResponse(this.cARCLFINIService.create(cARCLFINI));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICARCLFINI>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(cARCLFINI: ICARCLFINI): void {
    this.editForm.patchValue({
      id: cARCLFINI.id,
      classe: cARCLFINI.classe,
    });
  }

  protected createFromForm(): ICARCLFINI {
    return {
      ...new CARCLFINI(),
      id: this.editForm.get(['id'])!.value,
      classe: this.editForm.get(['classe'])!.value,
    };
  }
}
