import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CARCLFINIComponent } from './list/carclfini.component';
import { CARCLFINIDetailComponent } from './detail/carclfini-detail.component';
import { CARCLFINIUpdateComponent } from './update/carclfini-update.component';
import { CARCLFINIDeleteDialogComponent } from './delete/carclfini-delete-dialog.component';
import { CARCLFINIRoutingModule } from './route/carclfini-routing.module';

@NgModule({
  imports: [SharedModule, CARCLFINIRoutingModule],
  declarations: [CARCLFINIComponent, CARCLFINIDetailComponent, CARCLFINIUpdateComponent, CARCLFINIDeleteDialogComponent],
  entryComponents: [CARCLFINIDeleteDialogComponent],
})
export class CARCLFINIModule {}
