import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ParametersComponent } from '../list/parameters.component';
import { ParametersDetailComponent } from '../detail/parameters-detail.component';
import { ParametersUpdateComponent } from '../update/parameters-update.component';
import { ParametersRoutingResolveService } from './parameters-routing-resolve.service';

const parametersRoute: Routes = [
  {
    path: '',
    component: ParametersComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ParametersDetailComponent,
    resolve: {
      parameters: ParametersRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ParametersUpdateComponent,
    resolve: {
      parameters: ParametersRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ParametersUpdateComponent,
    resolve: {
      parameters: ParametersRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(parametersRoute)],
  exports: [RouterModule],
})
export class ParametersRoutingModule {}
