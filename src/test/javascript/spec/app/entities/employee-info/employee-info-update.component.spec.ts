import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MicroAppsTestModule } from '../../../test.module';
import { EmployeeInfoUpdateComponent } from 'app/entities/employee-info/employee-info-update.component';
import { EmployeeInfoService } from 'app/entities/employee-info/employee-info.service';
import { EmployeeInfo } from 'app/shared/model/employee-info.model';

describe('Component Tests', () => {
  describe('EmployeeInfo Management Update Component', () => {
    let comp: EmployeeInfoUpdateComponent;
    let fixture: ComponentFixture<EmployeeInfoUpdateComponent>;
    let service: EmployeeInfoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MicroAppsTestModule],
        declarations: [EmployeeInfoUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(EmployeeInfoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmployeeInfoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmployeeInfoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EmployeeInfo(123);
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
        const entity = new EmployeeInfo();
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
