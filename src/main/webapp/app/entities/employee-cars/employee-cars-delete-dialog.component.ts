import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEmployeeCars } from 'app/shared/model/employee-cars.model';
import { EmployeeCarsService } from './employee-cars.service';

@Component({
  selector: 'jhi-employee-cars-delete-dialog',
  templateUrl: './employee-cars-delete-dialog.component.html'
})
export class EmployeeCarsDeleteDialogComponent {
  employeeCars: IEmployeeCars;

  constructor(
    protected employeeCarsService: EmployeeCarsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.employeeCarsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'employeeCarsListModification',
        content: 'Deleted an employeeCars'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-employee-cars-delete-popup',
  template: ''
})
export class EmployeeCarsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ employeeCars }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(EmployeeCarsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.employeeCars = employeeCars;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/employee-cars', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/employee-cars', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
