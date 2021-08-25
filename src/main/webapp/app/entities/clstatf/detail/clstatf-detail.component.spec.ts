import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CLSTATFDetailComponent } from './clstatf-detail.component';

describe('Component Tests', () => {
  describe('CLSTATF Management Detail Component', () => {
    let comp: CLSTATFDetailComponent;
    let fixture: ComponentFixture<CLSTATFDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [CLSTATFDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ cLSTATF: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(CLSTATFDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CLSTATFDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load cLSTATF on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.cLSTATF).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
