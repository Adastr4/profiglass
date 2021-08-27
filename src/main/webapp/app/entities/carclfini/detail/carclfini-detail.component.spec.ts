import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CARCLFINIDetailComponent } from './carclfini-detail.component';

describe('Component Tests', () => {
  describe('CARCLFINI Management Detail Component', () => {
    let comp: CARCLFINIDetailComponent;
    let fixture: ComponentFixture<CARCLFINIDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [CARCLFINIDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ cARCLFINI: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(CARCLFINIDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CARCLFINIDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load cARCLFINI on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.cARCLFINI).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
