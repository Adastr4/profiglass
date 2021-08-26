import { Component, OnInit } from '@angular/core';
import { Subject } from 'rxjs';

import { ConstraintService } from 'app/constraint-selection/constraint.service';

@Component({
  selector: 'jhi-contraint-selection',
  templateUrl: './constraint.component.html',
  styleUrls: ['./constraint.component.scss'],
})
export class ConstraintComponent implements OnInit {
  cllegasData: any = {};
  clstatfslegaData: any = {};
  selectedDeviceCllegas = '';
  selectedDeviceClstatfslega = '';

  private readonly destroy$ = new Subject<void>();

  constructor(private constraintService: ConstraintService) {}

  onChangeCllegas(newObj: any): void {
    this.selectedDeviceCllegas = newObj;

    this.constraintService.getClstatfslega(newObj.opzione).subscribe(response => {
      this.clstatfslegaData = response.body;
    });
  }

  onChangeClstatfslega(newObj: any): void {
    this.selectedDeviceClstatfslega = newObj;
  }

  ngOnInit(): void {
    this.constraintService.getCllegas().subscribe(response => {
      this.cllegasData = response.body;
    });
  }
}
