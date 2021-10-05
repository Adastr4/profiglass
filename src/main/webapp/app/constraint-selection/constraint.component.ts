import { Component, OnInit } from '@angular/core';
import { Subject } from 'rxjs';

import { ConstraintService } from 'app/constraint-selection/constraint.service';
import { TransferDataService } from 'app/util/constraint-tree-view-service';

@Component({
  selector: 'jhi-contraint-selection',
  templateUrl: './constraint.component.html',
  styleUrls: ['./constraint.component.scss'],
})
export class ConstraintComponent implements OnInit {
  cllegasData: any = {};
  clstatfslegaData: any = {};
  clstatfsFinituraData: any = {};
  selectedDeviceCllegas: any = {};
  selectedDeviceClstatfslega: any = {};
  selectedDeviceClstatfsFinitura: any = {};

  private readonly destroy$ = new Subject<void>();

  constructor(private constraintService: ConstraintService, private transferDataService: TransferDataService) {}

  onChangeCllegas(newObj: any): void {
    this.selectedDeviceCllegas = newObj;
    this.transferDataService.setLega(this.selectedDeviceCllegas.opzione);

    this.constraintService.getClstatfslega(this.selectedDeviceCllegas.opzione).subscribe(response => {
      this.clstatfslegaData = response.body;
    });
  }

  onChangeClstatfslega(newObj: any): void {
    this.selectedDeviceClstatfslega = newObj;
    this.transferDataService.setStato(this.selectedDeviceClstatfslega.opzione);

    this.constraintService
      .getClstatfsFinitura(this.selectedDeviceCllegas.opzione, this.selectedDeviceClstatfslega.opzione)
      .subscribe(response => {
        this.clstatfsFinituraData = response.body;
      });
  }

  onChangeClstatfsFinitura(newObj: any): void {
    this.selectedDeviceClstatfsFinitura = newObj;
    this.transferDataService.setFinitura(this.selectedDeviceClstatfsFinitura.classe);
  }

  ngOnInit(): void {
    this.constraintService.getCllegas().subscribe(response => {
      this.cllegasData = response.body;
    });
  }
}
