import { Component, OnInit } from '@angular/core';
import { Subject } from 'rxjs';

import { ConstraintService } from 'app/constraint-selection/constraint.service';

@Component({
  selector: 'jhi-contraint-selection',
  templateUrl: './constraint.component.html',
  styleUrls: ['./constraint.component.scss'],
})
export class ConstraintComponent implements OnInit {
  query1: any = {};
  query2: any = {};
  selectedDeviceObj = '';
  selectedDeviceObj2 = '';

  private readonly destroy$ = new Subject<void>();

  constructor(private constraintService: ConstraintService) {}

  onChangeObj(newObj: any): void {
    this.selectedDeviceObj = newObj;

    this.constraintService.getTest2(newObj.opzione).subscribe(response => {
      this.query2 = response.body;
    });
  }

  onChangeObj2(newObj: any): void {
    this.selectedDeviceObj2 = newObj;

    this.constraintService.getTest2(newObj.opzione).subscribe(response => {
      this.query2 = response.body;
    });
  }

  ngOnInit(): void {
    this.constraintService.getTest().subscribe(response => {
      this.query1 = response.body;
    });
  }
}
