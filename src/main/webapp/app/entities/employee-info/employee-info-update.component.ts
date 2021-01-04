import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEmployeeInfo, EmployeeInfo } from 'app/shared/model/employee-info.model';
import { EmployeeInfoService } from './employee-info.service';

@Component({
  selector: 'jhi-employee-info-update',
  templateUrl: './employee-info-update.component.html',
})
export class EmployeeInfoUpdateComponent implements OnInit {
  isSaving = false;
  dobDp: any;

  editForm = this.fb.group({
    id: [],
    empName: [],
    designation: [],
    mobile: [],
    dob: [],
    remarks: [],
  });

  constructor(protected employeeInfoService: EmployeeInfoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employeeInfo }) => {
      this.updateForm(employeeInfo);
    });
  }

  updateForm(employeeInfo: IEmployeeInfo): void {
    this.editForm.patchValue({
      id: employeeInfo.id,
      empName: employeeInfo.empName,
      designation: employeeInfo.designation,
      mobile: employeeInfo.mobile,
      dob: employeeInfo.dob,
      remarks: employeeInfo.remarks,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const employeeInfo = this.createFromForm();
    if (employeeInfo.id !== undefined) {
      this.subscribeToSaveResponse(this.employeeInfoService.update(employeeInfo));
    } else {
      this.subscribeToSaveResponse(this.employeeInfoService.create(employeeInfo));
    }
  }

  private createFromForm(): IEmployeeInfo {
    return {
      ...new EmployeeInfo(),
      id: this.editForm.get(['id'])!.value,
      empName: this.editForm.get(['empName'])!.value,
      designation: this.editForm.get(['designation'])!.value,
      mobile: this.editForm.get(['mobile'])!.value,
      dob: this.editForm.get(['dob'])!.value,
      remarks: this.editForm.get(['remarks'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmployeeInfo>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
