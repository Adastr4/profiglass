import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICLLEGA } from '../cllega.model';

@Component({
  selector: 'jhi-cllega-detail',
  templateUrl: './cllega-detail.component.html',
})
export class CLLEGADetailComponent implements OnInit {
  cLLEGA: ICLLEGA | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cLLEGA }) => {
      this.cLLEGA = cLLEGA;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
