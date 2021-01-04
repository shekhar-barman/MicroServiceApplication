import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEmployeeInfo, EmployeeInfo } from 'app/shared/model/employee-info.model';
import { EmployeeInfoService } from './employee-info.service';
import { EmployeeInfoComponent } from './employee-info.component';
import { EmployeeInfoDetailComponent } from './employee-info-detail.component';
import { EmployeeInfoUpdateComponent } from './employee-info-update.component';

@Injectable({ providedIn: 'root' })
export class EmployeeInfoResolve implements Resolve<IEmployeeInfo> {
  constructor(private service: EmployeeInfoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEmployeeInfo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((employeeInfo: HttpResponse<EmployeeInfo>) => {
          if (employeeInfo.body) {
            return of(employeeInfo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EmployeeInfo());
  }
}

export const employeeInfoRoute: Routes = [
  {
    path: '',
    component: EmployeeInfoComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'microAppsApp.employeeInfo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EmployeeInfoDetailComponent,
    resolve: {
      employeeInfo: EmployeeInfoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'microAppsApp.employeeInfo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EmployeeInfoUpdateComponent,
    resolve: {
      employeeInfo: EmployeeInfoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'microAppsApp.employeeInfo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EmployeeInfoUpdateComponent,
    resolve: {
      employeeInfo: EmployeeInfoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'microAppsApp.employeeInfo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
