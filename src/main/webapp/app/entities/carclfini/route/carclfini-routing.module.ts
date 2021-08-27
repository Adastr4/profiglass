import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CARCLFINIComponent } from '../list/carclfini.component';
import { CARCLFINIDetailComponent } from '../detail/carclfini-detail.component';
import { CARCLFINIUpdateComponent } from '../update/carclfini-update.component';
import { CARCLFINIRoutingResolveService } from './carclfini-routing-resolve.service';

const cARCLFINIRoute: Routes = [
  {
    path: '',
    component: CARCLFINIComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CARCLFINIDetailComponent,
    resolve: {
      cARCLFINI: CARCLFINIRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CARCLFINIUpdateComponent,
    resolve: {
      cARCLFINI: CARCLFINIRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CARCLFINIUpdateComponent,
    resolve: {
      cARCLFINI: CARCLFINIRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(cARCLFINIRoute)],
  exports: [RouterModule],
})
export class CARCLFINIRoutingModule {}
