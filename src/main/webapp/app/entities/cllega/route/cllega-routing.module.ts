import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CLLEGAComponent } from '../list/cllega.component';
import { CLLEGADetailComponent } from '../detail/cllega-detail.component';
import { CLLEGAUpdateComponent } from '../update/cllega-update.component';
import { CLLEGARoutingResolveService } from './cllega-routing-resolve.service';

const cLLEGARoute: Routes = [
  {
    path: '',
    component: CLLEGAComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CLLEGADetailComponent,
    resolve: {
      cLLEGA: CLLEGARoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CLLEGAUpdateComponent,
    resolve: {
      cLLEGA: CLLEGARoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CLLEGAUpdateComponent,
    resolve: {
      cLLEGA: CLLEGARoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(cLLEGARoute)],
  exports: [RouterModule],
})
export class CLLEGARoutingModule {}
