import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CLSTATFComponent } from '../list/clstatf.component';
import { CLSTATFDetailComponent } from '../detail/clstatf-detail.component';
import { CLSTATFUpdateComponent } from '../update/clstatf-update.component';
import { CLSTATFRoutingResolveService } from './clstatf-routing-resolve.service';

const cLSTATFRoute: Routes = [
  {
    path: '',
    component: CLSTATFComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CLSTATFDetailComponent,
    resolve: {
      cLSTATF: CLSTATFRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CLSTATFUpdateComponent,
    resolve: {
      cLSTATF: CLSTATFRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CLSTATFUpdateComponent,
    resolve: {
      cLSTATF: CLSTATFRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(cLSTATFRoute)],
  exports: [RouterModule],
})
export class CLSTATFRoutingModule {}
