import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'parameters',
        data: { pageTitle: 'profilglassApp.parameters.home.title' },
        loadChildren: () => import('./parameters/parameters.module').then(m => m.ParametersModule),
      },
      {
        path: 'cllega',
        data: { pageTitle: 'profilglassApp.cLLEGA.home.title' },
        loadChildren: () => import('./cllega/cllega.module').then(m => m.CLLEGAModule),
      },
      {
        path: 'clstatf',
        data: { pageTitle: 'profilglassApp.cLSTATF.home.title' },
        loadChildren: () => import('./clstatf/clstatf.module').then(m => m.CLSTATFModule),
      },
      {
        path: 'carclfini',
        data: { pageTitle: 'profilglassApp.cARCLFINI.home.title' },
        loadChildren: () => import('./carclfini/carclfini.module').then(m => m.CARCLFINIModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
