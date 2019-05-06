import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CarTrackerSharedModule } from 'app/shared';
import {
  BranchComponent,
  BranchDetailComponent,
  BranchUpdateComponent,
  BranchDeletePopupComponent,
  BranchDeleteDialogComponent,
  branchRoute,
  branchPopupRoute
} from './';

const ENTITY_STATES = [...branchRoute, ...branchPopupRoute];

@NgModule({
  imports: [CarTrackerSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [BranchComponent, BranchDetailComponent, BranchUpdateComponent, BranchDeleteDialogComponent, BranchDeletePopupComponent],
  entryComponents: [BranchComponent, BranchUpdateComponent, BranchDeleteDialogComponent, BranchDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CarTrackerBranchModule {}
