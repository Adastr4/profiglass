import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';
import { ConstraintComponent } from 'app/constraint-selection/constraint.component';
import { TreeViewComponent } from 'app/tree-view/tree-view.component';
import { TreeviewModule } from 'ngx-treeview';
import { DisabledOnSelectorDirective } from 'app/disabled-on-selector.directive';

@NgModule({
  imports: [SharedModule, RouterModule.forChild([HOME_ROUTE]), TreeviewModule.forRoot()],
  declarations: [HomeComponent, ConstraintComponent, TreeViewComponent, DisabledOnSelectorDirective],
})
export class HomeModule {}
