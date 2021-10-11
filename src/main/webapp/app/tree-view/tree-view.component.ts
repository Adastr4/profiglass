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

  /* eslint-disable no-console */
  ngOnInit(): void {
    this.items = this.service.getBaseTreeView();

    this.transferDataService.subject.subscribe(data => {
      this.data = data;
      this.service.getConfiguration(this.data.lega, this.data.stato, this.data.finitura).subscribe(response => {
        this.items[0].children[0].children = this.service.getConfigTreeView(response.body);
      });
    });
  }

  onFilterChange(value: string): void {
    console.log('filter:', value);
  }
}
