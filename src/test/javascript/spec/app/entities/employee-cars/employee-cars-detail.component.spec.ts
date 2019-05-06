/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CarTrackerTestModule } from '../../../test.module';
import { EmployeeCarsDetailComponent } from 'app/entities/employee-cars/employee-cars-detail.component';
import { EmployeeCars } from 'app/shared/model/employee-cars.model';

describe('Component Tests', () => {
  describe('EmployeeCars Management Detail Component', () => {
    let comp: EmployeeCarsDetailComponent;
    let fixture: ComponentFixture<EmployeeCarsDetailComponent>;
    const route = ({ data: of({ employeeCars: new EmployeeCars(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CarTrackerTestModule],
        declarations: [EmployeeCarsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EmployeeCarsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EmployeeCarsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.employeeCars).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
