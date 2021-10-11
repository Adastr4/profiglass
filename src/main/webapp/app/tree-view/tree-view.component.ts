import { Component, OnInit } from '@angular/core';
import { TreeviewItem, TreeviewConfig } from 'ngx-treeview';
import { TreeViewService } from './tree-view.service';
import { TransferDataService } from 'app/util/constraint-tree-view-service';

@Component({
  selector: 'jhi-tree-view',
  templateUrl: './tree-view.component.html',
})
export class TreeViewComponent implements OnInit {
  dropdownEnabled = true;
  items: TreeviewItem[] = [];
  values: number[] = [];
  config = TreeviewConfig.create({
    hasAllCheckBox: false,
    hasFilter: true,
    hasCollapseExpand: true,
    decoupleChildFromParent: false,
    maxHeight: 400,
  });

  data: any = undefined;
  testView: any;
  testConfig: any;
  buttonClasses = [
    'btn-outline-primary',
    'btn-outline-secondary',
    'btn-outline-success',
    'btn-outline-danger',
    'btn-outline-warning',
    'btn-outline-info',
    'btn-outline-light',
    'btn-outline-dark',
  ];
  buttonClass = this.buttonClasses[0];

  constructor(private service: TreeViewService, private transferDataService: TransferDataService) {}

  ngOnInit(): void {
    this.transferDataService.subject.subscribe(data => {
      this.data = data;
      /* eslint-disable no-console */
      this.service.getClstatfsFinitura(this.data.lega, this.data.stato, this.data.finitura).subscribe(response => {
        console.log('Response:', response.body);
        this.testView = JSON.stringify(response.body);
      });
      this.service.getConfiguration(this.data.lega, this.data.stato, this.data.finitura).subscribe(response => {
        console.log('Response:', response.body);
        this.testConfig = JSON.stringify(response.body);
      });
      /* eslint-enable no-console */
    });
    /**
     * TEST
     */
    this.service.getConfiguration('3D', 'H14', 'I').subscribe(response => {
      /* eslint-disable no-console */
      //console.log('Response:', response.body);
      this.testConfig = response.body;
      const caratteristica: any = [];
      this.testConfig.caratteristiche.forEach((car: any) => {
        //console.log("classe: ", car.caratteristicaId);
        const valCaratteristica: any = [];
        car.valori.forEach((val: any) => {
          const childVal = {
            text: `Opzione: ${String(val.opzione)} | Descrizione: ${String(val.descrizione)}`,
            value: car.caratteristicaOrder * 10,
            checked: false,
          };
          valCaratteristica.push(childVal);
        });
        const childCaratteristica = {
          text: `ID: ${String(car.caratteristicaId)} | Classe: ${String(car.classe)}`,
          value: car.caratteristicaOrder,
          children: valCaratteristica,
          checked: false,
        };
        caratteristica.push(childCaratteristica);
      });
      this.items = [
        new TreeviewItem({
          text: 'Configuratore',
          value: 9,
          children: [
            {
              text: 'Caratteristica',
              value: 911,
              children: caratteristica,
            },
            {
              text: 'Ciclo',
              value: 911,
              children: [],
            },
            {
              text: 'Distinta',
              value: 912,
              children: [],
            },
          ],
        }),
      ];
      /* eslint-disable no-console */
    });
    /**
     * FINE TEST
     */
    //this.items = this.service.getBooks();
  }

  onFilterChange(value: string): void {
    //console.error('filter:', value);
  }
}
