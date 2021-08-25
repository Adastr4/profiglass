import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ICLSTATF, CLSTATF } from '../clstatf.model';
import { CLSTATFService } from '../service/clstatf.service';

@Component({
  selector: 'jhi-clstatf-update',
  templateUrl: './clstatf-update.component.html',
})
export class CLSTATFUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    opzione: [null, [Validators.required]],
    descrizione: [],
  });

  constructor(protected cLSTATFService: CLSTATFService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cLSTATF }) => {
      this.updateForm(cLSTATF);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cLSTATF = this.createFromForm();
    if (cLSTATF.id !== undefined) {
      this.subscribeToSaveResponse(this.cLSTATFService.update(cLSTATF));
    } else {
      this.subscribeToSaveResponse(this.cLSTATFService.create(cLSTATF));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICLSTATF>>): void {
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

  protected updateForm(cLSTATF: ICLSTATF): void {
    this.editForm.patchValue({
      id: cLSTATF.id,
      opzione: cLSTATF.opzione,
      descrizione: cLSTATF.descrizione,
    });
  }

  protected createFromForm(): ICLSTATF {
    return {
      ...new CLSTATF(),
      id: this.editForm.get(['id'])!.value,
      opzione: this.editForm.get(['opzione'])!.value,
      descrizione: this.editForm.get(['descrizione'])!.value,
    };
  }
}
