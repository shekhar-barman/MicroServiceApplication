import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmployeeInfo } from 'app/shared/model/employee-info.model';

@Component({
  selector: 'jhi-employee-info-detail',
  templateUrl: './employee-info-detail.component.html',
})
export class EmployeeInfoDetailComponent implements OnInit {
  employeeInfo: IEmployeeInfo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employeeInfo }) => (this.employeeInfo = employeeInfo));
  }

  previousState(): void {
    window.history.back();
  }
}
