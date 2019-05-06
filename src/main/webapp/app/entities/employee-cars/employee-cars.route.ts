import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { EmployeeCars } from 'app/shared/model/employee-cars.model';
import { EmployeeCarsService } from './employee-cars.service';
import { EmployeeCarsComponent } from './employee-cars.component';
import { EmployeeCarsDetailComponent } from './employee-cars-detail.component';
import { EmployeeCarsUpdateComponent } from './employee-cars-update.component';
import { EmployeeCarsDeletePopupComponent } from './employee-cars-delete-dialog.component';
import { IEmployeeCars } from 'app/shared/model/employee-cars.model';

@Injectable({ providedIn: 'root' })
export class EmployeeCarsResolve implements Resolve<IEmployeeCars> {
  constructor(private service: EmployeeCarsService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEmployeeCars> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<EmployeeCars>) => response.ok),
        map((employeeCars: HttpResponse<EmployeeCars>) => employeeCars.body)
      );
    }
    return of(new EmployeeCars());
  }
}

export const employeeCarsRoute: Routes = [
  {
    path: '',
    component: EmployeeCarsComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'EmployeeCars'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EmployeeCarsDetailComponent,
    resolve: {
      employeeCars: EmployeeCarsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'EmployeeCars'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EmployeeCarsUpdateComponent,
    resolve: {
      employeeCars: EmployeeCarsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'EmployeeCars'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EmployeeCarsUpdateComponent,
    resolve: {
      employeeCars: EmployeeCarsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'EmployeeCars'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const employeeCarsPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: EmployeeCarsDeletePopupComponent,
    resolve: {
      employeeCars: EmployeeCarsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'EmployeeCars'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
