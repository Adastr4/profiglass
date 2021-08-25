jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ParametersService } from '../service/parameters.service';
import { IParameters, Parameters } from '../parameters.model';

import { ParametersUpdateComponent } from './parameters-update.component';

describe('Component Tests', () => {
  describe('Parameters Management Update Component', () => {
    let comp: ParametersUpdateComponent;
    let fixture: ComponentFixture<ParametersUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let parametersService: ParametersService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ParametersUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ParametersUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ParametersUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      parametersService = TestBed.inject(ParametersService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const parameters: IParameters = { id: 456 };

        activatedRoute.data = of({ parameters });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(parameters));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Parameters>>();
        const parameters = { id: 123 };
        jest.spyOn(parametersService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ parameters });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: parameters }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(parametersService.update).toHaveBeenCalledWith(parameters);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Parameters>>();
        const parameters = new Parameters();
        jest.spyOn(parametersService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ parameters });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: parameters }));
        saveSubject.complete();

        // THEN
        expect(parametersService.create).toHaveBeenCalledWith(parameters);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Parameters>>();
        const parameters = { id: 123 };
        jest.spyOn(parametersService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ parameters });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(parametersService.update).toHaveBeenCalledWith(parameters);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
