import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IParameters } from '../parameters.model';

@Component({
  selector: 'jhi-parameters-detail',
  templateUrl: './parameters-detail.component.html',
})
export class ParametersDetailComponent implements OnInit {
  parameters: IParameters | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ parameters }) => {
      this.parameters = parameters;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
