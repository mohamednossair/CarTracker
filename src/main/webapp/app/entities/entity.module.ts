import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'branch',
        loadChildren: './branch/branch.module#CarTrackerBranchModule'
      },
      {
        path: 'company',
        loadChildren: './company/company.module#CarTrackerCompanyModule'
      },
      {
        path: 'employee',
        loadChildren: './employee/employee.module#CarTrackerEmployeeModule'
      },
      {
        path: 'car',
        loadChildren: './car/car.module#CarTrackerCarModule'
      },
      {
        path: 'employee-cars',
        loadChildren: './employee-cars/employee-cars.module#CarTrackerEmployeeCarsModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CarTrackerEntityModule {}
