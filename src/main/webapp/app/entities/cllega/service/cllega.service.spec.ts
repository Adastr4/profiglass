import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICLLEGA, CLLEGA } from '../cllega.model';

import { CLLEGAService } from './cllega.service';

describe('Service Tests', () => {
  describe('CLLEGA Service', () => {
    let service: CLLEGAService;
    let httpMock: HttpTestingController;
    let elemDefault: ICLLEGA;
    let expectedResult: ICLLEGA | ICLLEGA[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(CLLEGAService);
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

      it('should create a CLLEGA', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new CLLEGA()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CLLEGA', () => {
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

      it('should partial update a CLLEGA', () => {
        const patchObject = Object.assign(
          {
            opzione: 'BBBBBB',
            descrizione: 'BBBBBB',
          },
          new CLLEGA()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CLLEGA', () => {
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

      it('should delete a CLLEGA', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addCLLEGAToCollectionIfMissing', () => {
        it('should add a CLLEGA to an empty array', () => {
          const cLLEGA: ICLLEGA = { id: 123 };
          expectedResult = service.addCLLEGAToCollectionIfMissing([], cLLEGA);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(cLLEGA);
        });

        it('should not add a CLLEGA to an array that contains it', () => {
          const cLLEGA: ICLLEGA = { id: 123 };
          const cLLEGACollection: ICLLEGA[] = [
            {
              ...cLLEGA,
            },
            { id: 456 },
          ];
          expectedResult = service.addCLLEGAToCollectionIfMissing(cLLEGACollection, cLLEGA);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a CLLEGA to an array that doesn't contain it", () => {
          const cLLEGA: ICLLEGA = { id: 123 };
          const cLLEGACollection: ICLLEGA[] = [{ id: 456 }];
          expectedResult = service.addCLLEGAToCollectionIfMissing(cLLEGACollection, cLLEGA);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(cLLEGA);
        });

        it('should add only unique CLLEGA to an array', () => {
          const cLLEGAArray: ICLLEGA[] = [{ id: 123 }, { id: 456 }, { id: 52990 }];
          const cLLEGACollection: ICLLEGA[] = [{ id: 123 }];
          expectedResult = service.addCLLEGAToCollectionIfMissing(cLLEGACollection, ...cLLEGAArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const cLLEGA: ICLLEGA = { id: 123 };
          const cLLEGA2: ICLLEGA = { id: 456 };
          expectedResult = service.addCLLEGAToCollectionIfMissing([], cLLEGA, cLLEGA2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(cLLEGA);
          expect(expectedResult).toContain(cLLEGA2);
        });

        it('should accept null and undefined values', () => {
          const cLLEGA: ICLLEGA = { id: 123 };
          expectedResult = service.addCLLEGAToCollectionIfMissing([], null, cLLEGA, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(cLLEGA);
        });

        it('should return initial array if no CLLEGA is added', () => {
          const cLLEGACollection: ICLLEGA[] = [{ id: 123 }];
          expectedResult = service.addCLLEGAToCollectionIfMissing(cLLEGACollection, undefined, null);
          expect(expectedResult).toEqual(cLLEGACollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
