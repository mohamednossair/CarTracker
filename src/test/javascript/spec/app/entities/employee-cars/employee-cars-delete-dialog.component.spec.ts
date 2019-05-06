/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CarTrackerTestModule } from '../../../test.module';
import { EmployeeCarsDeleteDialogComponent } from 'app/entities/employee-cars/employee-cars-delete-dialog.component';
import { EmployeeCarsService } from 'app/entities/employee-cars/employee-cars.service';

describe('Component Tests', () => {
  describe('EmployeeCars Management Delete Component', () => {
    let comp: EmployeeCarsDeleteDialogComponent;
    let fixture: ComponentFixture<EmployeeCarsDeleteDialogComponent>;
    let service: EmployeeCarsService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CarTrackerTestModule],
        declarations: [EmployeeCarsDeleteDialogComponent]
      })
        .overrideTemplate(EmployeeCarsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EmployeeCarsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmployeeCarsService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
