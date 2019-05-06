import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEmployeeCars } from 'app/shared/model/employee-cars.model';

type EntityResponseType = HttpResponse<IEmployeeCars>;
type EntityArrayResponseType = HttpResponse<IEmployeeCars[]>;

@Injectable({ providedIn: 'root' })
export class EmployeeCarsService {
  public resourceUrl = SERVER_API_URL + 'api/employee-cars';

  constructor(protected http: HttpClient) {}

  create(employeeCars: IEmployeeCars): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(employeeCars);
    return this.http
      .post<IEmployeeCars>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(employeeCars: IEmployeeCars): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(employeeCars);
    return this.http
      .put<IEmployeeCars>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEmployeeCars>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEmployeeCars[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(employeeCars: IEmployeeCars): IEmployeeCars {
    const copy: IEmployeeCars = Object.assign({}, employeeCars, {
      updateDate: employeeCars.updateDate != null && employeeCars.updateDate.isValid() ? employeeCars.updateDate.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.updateDate = res.body.updateDate != null ? moment(res.body.updateDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((employeeCars: IEmployeeCars) => {
        employeeCars.updateDate = employeeCars.updateDate != null ? moment(employeeCars.updateDate) : null;
      });
    }
    return res;
  }
}
