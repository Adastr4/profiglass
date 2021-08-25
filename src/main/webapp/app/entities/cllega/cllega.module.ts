import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CLLEGAComponent } from './list/cllega.component';
import { CLLEGADetailComponent } from './detail/cllega-detail.component';
import { CLLEGAUpdateComponent } from './update/cllega-update.component';
import { CLLEGADeleteDialogComponent } from './delete/cllega-delete-dialog.component';
import { CLLEGARoutingModule } from './route/cllega-routing.module';

@NgModule({
  imports: [SharedModule, CLLEGARoutingModule],
  declarations: [CLLEGAComponent, CLLEGADetailComponent, CLLEGAUpdateComponent, CLLEGADeleteDialogComponent],
  entryComponents: [CLLEGADeleteDialogComponent],
})
export class CLLEGAModule {}
