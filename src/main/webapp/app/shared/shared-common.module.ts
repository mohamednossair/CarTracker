import { NgModule } from '@angular/core';

import { CarTrackerSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
  imports: [CarTrackerSharedLibsModule],
  declarations: [JhiAlertComponent, JhiAlertErrorComponent],
  exports: [CarTrackerSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class CarTrackerSharedCommonModule {}
