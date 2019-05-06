import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmployeeCars } from 'app/shared/model/employee-cars.model';

@Component({
  selector: 'jhi-employee-cars-detail',
  templateUrl: './employee-cars-detail.component.html'
})
export class EmployeeCarsDetailComponent implements OnInit {
  employeeCars: IEmployeeCars;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ employeeCars }) => {
      this.employeeCars = employeeCars;
    });
  }

  previousState() {
    window.history.back();
  }
}
