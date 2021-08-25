import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CLSTATFComponent } from './list/clstatf.component';
import { CLSTATFDetailComponent } from './detail/clstatf-detail.component';
import { CLSTATFUpdateComponent } from './update/clstatf-update.component';
import { CLSTATFDeleteDialogComponent } from './delete/clstatf-delete-dialog.component';
import { CLSTATFRoutingModule } from './route/clstatf-routing.module';

@NgModule({
  imports: [SharedModule, CLSTATFRoutingModule],
  declarations: [CLSTATFComponent, CLSTATFDetailComponent, CLSTATFUpdateComponent, CLSTATFDeleteDialogComponent],
  entryComponents: [CLSTATFDeleteDialogComponent],
})
export class CLSTATFModule {}
