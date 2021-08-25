import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ParametersComponent } from './list/parameters.component';
import { ParametersDetailComponent } from './detail/parameters-detail.component';
import { ParametersUpdateComponent } from './update/parameters-update.component';
import { ParametersDeleteDialogComponent } from './delete/parameters-delete-dialog.component';
import { ParametersRoutingModule } from './route/parameters-routing.module';

@NgModule({
  imports: [SharedModule, ParametersRoutingModule],
  declarations: [ParametersComponent, ParametersDetailComponent, ParametersUpdateComponent, ParametersDeleteDialogComponent],
  entryComponents: [ParametersDeleteDialogComponent],
})
export class ParametersModule {}
