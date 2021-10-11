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

  constructor(private service: TreeViewService, private transferDataService: TransferDataService) {}

  ngOnInit(): void {
    this.transferDataService.subject.subscribe(data => {
      this.data = data;
      /* eslint-disable no-console */
      //console.log(this.data.lega, this.data.stato, this.data.finitura);
      this.service.getConfiguration(this.data.lega, this.data.stato, this.data.finitura).subscribe(response => {
        //console.log('Response:', response.body);
        this.items = this.service.getConfigTreeView(response.body);
      });
    });
    /**
     * TEST
     *
    this.service.getConfiguration('3D', 'H14', 'I').subscribe(response => {
      this.items = this.service.getConfigTreeView(response.body);
    });
    /**
     * FINE TEST
     */
  }

  onFilterChange(value: string): void {
    console.log('filter:', value);
  }
}
