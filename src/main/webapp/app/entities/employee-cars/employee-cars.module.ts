import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CarTrackerSharedModule } from 'app/shared';
import {
  EmployeeCarsComponent,
  EmployeeCarsDetailComponent,
  EmployeeCarsUpdateComponent,
  EmployeeCarsDeletePopupComponent,
  EmployeeCarsDeleteDialogComponent,
  employeeCarsRoute,
  employeeCarsPopupRoute
} from './';

const ENTITY_STATES = [...employeeCarsRoute, ...employeeCarsPopupRoute];

@NgModule({
  imports: [CarTrackerSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    EmployeeCarsComponent,
    EmployeeCarsDetailComponent,
    EmployeeCarsUpdateComponent,
    EmployeeCarsDeleteDialogComponent,
    EmployeeCarsDeletePopupComponent
  ],
  entryComponents: [
    EmployeeCarsComponent,
    EmployeeCarsUpdateComponent,
    EmployeeCarsDeleteDialogComponent,
    EmployeeCarsDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CarTrackerEmployeeCarsModule {}
