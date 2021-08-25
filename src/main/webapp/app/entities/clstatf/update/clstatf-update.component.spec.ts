jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CLSTATFService } from '../service/clstatf.service';
import { ICLSTATF, CLSTATF } from '../clstatf.model';

import { CLSTATFUpdateComponent } from './clstatf-update.component';

describe('Component Tests', () => {
  describe('CLSTATF Management Update Component', () => {
    let comp: CLSTATFUpdateComponent;
    let fixture: ComponentFixture<CLSTATFUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let cLSTATFService: CLSTATFService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CLSTATFUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(CLSTATFUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CLSTATFUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      cLSTATFService = TestBed.inject(CLSTATFService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const cLSTATF: ICLSTATF = { id: 456 };

        activatedRoute.data = of({ cLSTATF });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(cLSTATF));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<CLSTATF>>();
        const cLSTATF = { id: 123 };
        jest.spyOn(cLSTATFService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ cLSTATF });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: cLSTATF }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(cLSTATFService.update).toHaveBeenCalledWith(cLSTATF);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<CLSTATF>>();
        const cLSTATF = new CLSTATF();
        jest.spyOn(cLSTATFService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ cLSTATF });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: cLSTATF }));
        saveSubject.complete();

        // THEN
        expect(cLSTATFService.create).toHaveBeenCalledWith(cLSTATF);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<CLSTATF>>();
        const cLSTATF = { id: 123 };
        jest.spyOn(cLSTATFService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ cLSTATF });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(cLSTATFService.update).toHaveBeenCalledWith(cLSTATF);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
