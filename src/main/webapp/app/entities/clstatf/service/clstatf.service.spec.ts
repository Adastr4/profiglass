import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICLSTATF, CLSTATF } from '../clstatf.model';

import { CLSTATFService } from './clstatf.service';

describe('Service Tests', () => {
  describe('CLSTATF Service', () => {
    let service: CLSTATFService;
    let httpMock: HttpTestingController;
    let elemDefault: ICLSTATF;
    let expectedResult: ICLSTATF | ICLSTATF[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(CLSTATFService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        opzione: 'AAAAAAA',
        descrizione: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a CLSTATF', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new CLSTATF()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CLSTATF', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            opzione: 'BBBBBB',
            descrizione: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a CLSTATF', () => {
        const patchObject = Object.assign(
          {
            descrizione: 'BBBBBB',
          },
          new CLSTATF()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CLSTATF', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            opzione: 'BBBBBB',
            descrizione: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a CLSTATF', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addCLSTATFToCollectionIfMissing', () => {
        it('should add a CLSTATF to an empty array', () => {
          const cLSTATF: ICLSTATF = { id: 123 };
          expectedResult = service.addCLSTATFToCollectionIfMissing([], cLSTATF);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(cLSTATF);
        });

        it('should not add a CLSTATF to an array that contains it', () => {
          const cLSTATF: ICLSTATF = { id: 123 };
          const cLSTATFCollection: ICLSTATF[] = [
            {
              ...cLSTATF,
            },
            { id: 456 },
          ];
          expectedResult = service.addCLSTATFToCollectionIfMissing(cLSTATFCollection, cLSTATF);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a CLSTATF to an array that doesn't contain it", () => {
          const cLSTATF: ICLSTATF = { id: 123 };
          const cLSTATFCollection: ICLSTATF[] = [{ id: 456 }];
          expectedResult = service.addCLSTATFToCollectionIfMissing(cLSTATFCollection, cLSTATF);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(cLSTATF);
        });

        it('should add only unique CLSTATF to an array', () => {
          const cLSTATFArray: ICLSTATF[] = [{ id: 123 }, { id: 456 }, { id: 70577 }];
          const cLSTATFCollection: ICLSTATF[] = [{ id: 123 }];
          expectedResult = service.addCLSTATFToCollectionIfMissing(cLSTATFCollection, ...cLSTATFArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const cLSTATF: ICLSTATF = { id: 123 };
          const cLSTATF2: ICLSTATF = { id: 456 };
          expectedResult = service.addCLSTATFToCollectionIfMissing([], cLSTATF, cLSTATF2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(cLSTATF);
          expect(expectedResult).toContain(cLSTATF2);
        });

        it('should accept null and undefined values', () => {
          const cLSTATF: ICLSTATF = { id: 123 };
          expectedResult = service.addCLSTATFToCollectionIfMissing([], null, cLSTATF, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(cLSTATF);
        });

        it('should return initial array if no CLSTATF is added', () => {
          const cLSTATFCollection: ICLSTATF[] = [{ id: 123 }];
          expectedResult = service.addCLSTATFToCollectionIfMissing(cLSTATFCollection, undefined, null);
          expect(expectedResult).toEqual(cLSTATFCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
