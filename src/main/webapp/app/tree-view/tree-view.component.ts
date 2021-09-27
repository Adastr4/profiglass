import { Component, OnInit } from '@angular/core';
import { TreeviewItem, TreeviewConfig } from 'ngx-treeview';
import { TreeViewService } from './tree-view.service';

@Component({
  selector: 'jhi-tree-view',
  templateUrl: './tree-view.component.html',
  providers: [TreeViewService],
})
export class TreeViewComponent implements OnInit {
  dropdownEnabled = true;
  items: TreeviewItem[] = [];
  values: number[] = [];
  config = TreeviewConfig.create({
    hasAllCheckBox: true,
    hasFilter: true,
    hasCollapseExpand: true,
    decoupleChildFromParent: false,
    maxHeight: 400,
  });

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

  constructor(private service: TreeViewService) {}

  ngOnInit(): void {
    this.items = this.service.getBooks();
  }

  onFilterChange(value: string): void {
    //console.log('filter:', value);
  }
}
