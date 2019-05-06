/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { CarTrackerTestModule } from '../../../test.module';
import { EmployeeCarsUpdateComponent } from 'app/entities/employee-cars/employee-cars-update.component';
import { EmployeeCarsService } from 'app/entities/employee-cars/employee-cars.service';
import { EmployeeCars } from 'app/shared/model/employee-cars.model';

describe('Component Tests', () => {
  describe('EmployeeCars Management Update Component', () => {
    let comp: EmployeeCarsUpdateComponent;
    let fixture: ComponentFixture<EmployeeCarsUpdateComponent>;
    let service: EmployeeCarsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CarTrackerTestModule],
        declarations: [EmployeeCarsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EmployeeCarsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmployeeCarsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmployeeCarsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EmployeeCars(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new EmployeeCars();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
