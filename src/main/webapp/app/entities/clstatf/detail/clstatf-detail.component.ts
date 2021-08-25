import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICLSTATF } from '../clstatf.model';

@Component({
  selector: 'jhi-clstatf-detail',
  templateUrl: './clstatf-detail.component.html',
})
export class CLSTATFDetailComponent implements OnInit {
  cLSTATF: ICLSTATF | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cLSTATF }) => {
      this.cLSTATF = cLSTATF;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
