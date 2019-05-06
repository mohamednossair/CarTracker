import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IEmployeeCars, EmployeeCars } from 'app/shared/model/employee-cars.model';
import { EmployeeCarsService } from './employee-cars.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee';
import { ICar } from 'app/shared/model/car.model';
import { CarService } from 'app/entities/car';

@Component({
  selector: 'jhi-employee-cars-update',
  templateUrl: './employee-cars-update.component.html'
})
export class EmployeeCarsUpdateComponent implements OnInit {
  employeeCars: IEmployeeCars;
  isSaving: boolean;

  employees: IEmployee[];

  cars: ICar[];
  updateDateDp: any;

  editForm = this.fb.group({
    id: [],
    previousReading: [null, [Validators.min(0)]],
    currentReading: [null, [Validators.min(0)]],
    workingDays: [],
    updateDate: [],
    employee: [null, Validators.required],
    car: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected employeeCarsService: EmployeeCarsService,
    protected employeeService: EmployeeService,
    protected carService: CarService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ employeeCars }) => {
      this.updateForm(employeeCars);
      this.employeeCars = employeeCars;
    });
    this.employeeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEmployee[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEmployee[]>) => response.body)
      )
      .subscribe((res: IEmployee[]) => (this.employees = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.carService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICar[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICar[]>) => response.body)
      )
      .subscribe((res: ICar[]) => (this.cars = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(employeeCars: IEmployeeCars) {
    this.editForm.patchValue({
      id: employeeCars.id,
      previousReading: employeeCars.previousReading,
      currentReading: employeeCars.currentReading,
      workingDays: employeeCars.workingDays,
      updateDate: employeeCars.updateDate,
      employee: employeeCars.employee,
      car: employeeCars.car
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const employeeCars = this.createFromForm();
    if (employeeCars.id !== undefined) {
      this.subscribeToSaveResponse(this.employeeCarsService.update(employeeCars));
    } else {
      this.subscribeToSaveResponse(this.employeeCarsService.create(employeeCars));
    }
  }

  private createFromForm(): IEmployeeCars {
    const entity = {
      ...new EmployeeCars(),
      id: this.editForm.get(['id']).value,
      previousReading: this.editForm.get(['previousReading']).value,
      currentReading: this.editForm.get(['currentReading']).value,
      workingDays: this.editForm.get(['workingDays']).value,
      updateDate: this.editForm.get(['updateDate']).value,
      employee: this.editForm.get(['employee']).value,
      car: this.editForm.get(['car']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmployeeCars>>) {
    result.subscribe((res: HttpResponse<IEmployeeCars>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackEmployeeById(index: number, item: IEmployee) {
    return item.id;
  }

  trackCarById(index: number, item: ICar) {
    return item.id;
  }
}
