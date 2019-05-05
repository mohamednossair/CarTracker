import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CarTrackerSharedLibsModule, CarTrackerSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [CarTrackerSharedLibsModule, CarTrackerSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [CarTrackerSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CarTrackerSharedModule {
  static forRoot() {
    return {
      ngModule: CarTrackerSharedModule
    };
  }
}
