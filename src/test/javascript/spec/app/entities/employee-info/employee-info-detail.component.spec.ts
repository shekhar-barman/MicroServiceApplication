import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MicroAppsTestModule } from '../../../test.module';
import { EmployeeInfoDetailComponent } from 'app/entities/employee-info/employee-info-detail.component';
import { EmployeeInfo } from 'app/shared/model/employee-info.model';

describe('Component Tests', () => {
  describe('EmployeeInfo Management Detail Component', () => {
    let comp: EmployeeInfoDetailComponent;
    let fixture: ComponentFixture<EmployeeInfoDetailComponent>;
    const route = ({ data: of({ employeeInfo: new EmployeeInfo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MicroAppsTestModule],
        declarations: [EmployeeInfoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(EmployeeInfoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EmployeeInfoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load employeeInfo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.employeeInfo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
