import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICARCLFINI } from '../carclfini.model';

@Component({
  selector: 'jhi-carclfini-detail',
  templateUrl: './carclfini-detail.component.html',
})
export class CARCLFINIDetailComponent implements OnInit {
  cARCLFINI: ICARCLFINI | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cARCLFINI }) => {
      this.cARCLFINI = cARCLFINI;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
