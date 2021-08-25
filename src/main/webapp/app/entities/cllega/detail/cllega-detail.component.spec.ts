import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CLLEGADetailComponent } from './cllega-detail.component';

describe('Component Tests', () => {
  describe('CLLEGA Management Detail Component', () => {
    let comp: CLLEGADetailComponent;
    let fixture: ComponentFixture<CLLEGADetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [CLLEGADetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ cLLEGA: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(CLLEGADetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CLLEGADetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load cLLEGA on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.cLLEGA).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
