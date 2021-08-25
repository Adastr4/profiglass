jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CLLEGAService } from '../service/cllega.service';
import { ICLLEGA, CLLEGA } from '../cllega.model';

import { CLLEGAUpdateComponent } from './cllega-update.component';

describe('Component Tests', () => {
  describe('CLLEGA Management Update Component', () => {
    let comp: CLLEGAUpdateComponent;
    let fixture: ComponentFixture<CLLEGAUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let cLLEGAService: CLLEGAService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CLLEGAUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(CLLEGAUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CLLEGAUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      cLLEGAService = TestBed.inject(CLLEGAService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const cLLEGA: ICLLEGA = { id: 456 };

        activatedRoute.data = of({ cLLEGA });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(cLLEGA));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<CLLEGA>>();
        const cLLEGA = { id: 123 };
        jest.spyOn(cLLEGAService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ cLLEGA });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: cLLEGA }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(cLLEGAService.update).toHaveBeenCalledWith(cLLEGA);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<CLLEGA>>();
        const cLLEGA = new CLLEGA();
        jest.spyOn(cLLEGAService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ cLLEGA });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: cLLEGA }));
        saveSubject.complete();

        // THEN
        expect(cLLEGAService.create).toHaveBeenCalledWith(cLLEGA);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<CLLEGA>>();
        const cLLEGA = { id: 123 };
        jest.spyOn(cLLEGAService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ cLLEGA });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(cLLEGAService.update).toHaveBeenCalledWith(cLLEGA);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
