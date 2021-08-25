import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ICLLEGA, CLLEGA } from '../cllega.model';
import { CLLEGAService } from '../service/cllega.service';

@Component({
  selector: 'jhi-cllega-update',
  templateUrl: './cllega-update.component.html',
})
export class CLLEGAUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    opzione: [null, [Validators.required]],
    descrizione: [],
  });

  constructor(protected cLLEGAService: CLLEGAService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cLLEGA }) => {
      this.updateForm(cLLEGA);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cLLEGA = this.createFromForm();
    if (cLLEGA.id !== undefined) {
      this.subscribeToSaveResponse(this.cLLEGAService.update(cLLEGA));
    } else {
      this.subscribeToSaveResponse(this.cLLEGAService.create(cLLEGA));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICLLEGA>>): void {
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

  protected updateForm(cLLEGA: ICLLEGA): void {
    this.editForm.patchValue({
      id: cLLEGA.id,
      opzione: cLLEGA.opzione,
      descrizione: cLLEGA.descrizione,
    });
  }

  protected createFromForm(): ICLLEGA {
    return {
      ...new CLLEGA(),
      id: this.editForm.get(['id'])!.value,
      opzione: this.editForm.get(['opzione'])!.value,
      descrizione: this.editForm.get(['descrizione'])!.value,
    };
  }
}
