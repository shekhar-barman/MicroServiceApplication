import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEmployeeInfo } from 'app/shared/model/employee-info.model';
import { EmployeeInfoService } from './employee-info.service';

@Component({
  templateUrl: './employee-info-delete-dialog.component.html',
})
export class EmployeeInfoDeleteDialogComponent {
  employeeInfo?: IEmployeeInfo;

  constructor(
    protected employeeInfoService: EmployeeInfoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.employeeInfoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('employeeInfoListModification');
      this.activeModal.close();
    });
  }
}
