import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ICar, Car } from 'app/shared/model/car.model';
import { CarService } from './car.service';

@Component({
  selector: 'jhi-car-update',
  templateUrl: './car-update.component.html'
})
export class CarUpdateComponent implements OnInit {
  car: ICar;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required]],
    carType: [null, [Validators.required]],
    category: [],
    serialNum: [],
    hide: [],
    isNew: []
  });

  constructor(protected carService: CarService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ car }) => {
      this.updateForm(car);
      this.car = car;
    });
  }

  updateForm(car: ICar) {
    this.editForm.patchValue({
      id: car.id,
      code: car.code,
      carType: car.carType,
      category: car.category,
      serialNum: car.serialNum,
      hide: car.hide,
      isNew: car.isNew
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const car = this.createFromForm();
    if (car.id !== undefined) {
      this.subscribeToSaveResponse(this.carService.update(car));
    } else {
      this.subscribeToSaveResponse(this.carService.create(car));
    }
  }

  private createFromForm(): ICar {
    const entity = {
      ...new Car(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      carType: this.editForm.get(['carType']).value,
      category: this.editForm.get(['category']).value,
      serialNum: this.editForm.get(['serialNum']).value,
      hide: this.editForm.get(['hide']).value,
      isNew: this.editForm.get(['isNew']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICar>>) {
    result.subscribe((res: HttpResponse<ICar>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
