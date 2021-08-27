jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CARCLFINIService } from '../service/carclfini.service';
import { ICARCLFINI, CARCLFINI } from '../carclfini.model';

import { CARCLFINIUpdateComponent } from './carclfini-update.component';

describe('Component Tests', () => {
  describe('CARCLFINI Management Update Component', () => {
    let comp: CARCLFINIUpdateComponent;
    let fixture: ComponentFixture<CARCLFINIUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let cARCLFINIService: CARCLFINIService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CARCLFINIUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(CARCLFINIUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CARCLFINIUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      cARCLFINIService = TestBed.inject(CARCLFINIService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const cARCLFINI: ICARCLFINI = { id: 456 };

        activatedRoute.data = of({ cARCLFINI });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(cARCLFINI));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<CARCLFINI>>();
        const cARCLFINI = { id: 123 };
        jest.spyOn(cARCLFINIService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ cARCLFINI });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: cARCLFINI }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(cARCLFINIService.update).toHaveBeenCalledWith(cARCLFINI);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<CARCLFINI>>();
        const cARCLFINI = new CARCLFINI();
        jest.spyOn(cARCLFINIService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ cARCLFINI });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: cARCLFINI }));
        saveSubject.complete();

        // THEN
        expect(cARCLFINIService.create).toHaveBeenCalledWith(cARCLFINI);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<CARCLFINI>>();
        const cARCLFINI = { id: 123 };
        jest.spyOn(cARCLFINIService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ cARCLFINI });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(cARCLFINIService.update).toHaveBeenCalledWith(cARCLFINI);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
