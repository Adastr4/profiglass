import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ParametersDetailComponent } from './parameters-detail.component';

describe('Component Tests', () => {
  describe('Parameters Management Detail Component', () => {
    let comp: ParametersDetailComponent;
    let fixture: ComponentFixture<ParametersDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [ParametersDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ parameters: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(ParametersDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ParametersDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load parameters on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.parameters).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
