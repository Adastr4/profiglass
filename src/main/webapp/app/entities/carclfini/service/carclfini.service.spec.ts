import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICARCLFINI, CARCLFINI } from '../carclfini.model';

import { CARCLFINIService } from './carclfini.service';

describe('Service Tests', () => {
  describe('CARCLFINI Service', () => {
    let service: CARCLFINIService;
    let httpMock: HttpTestingController;
    let elemDefault: ICARCLFINI;
    let expectedResult: ICARCLFINI | ICARCLFINI[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(CARCLFINIService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        classe: 'AAAAAAA',
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

      it('should create a CARCLFINI', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new CARCLFINI()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CARCLFINI', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            classe: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a CARCLFINI', () => {
        const patchObject = Object.assign(
          {
            classe: 'BBBBBB',
          },
          new CARCLFINI()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CARCLFINI', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            classe: 'BBBBBB',
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

      it('should delete a CARCLFINI', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addCARCLFINIToCollectionIfMissing', () => {
        it('should add a CARCLFINI to an empty array', () => {
          const cARCLFINI: ICARCLFINI = { id: 123 };
          expectedResult = service.addCARCLFINIToCollectionIfMissing([], cARCLFINI);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(cARCLFINI);
        });

        it('should not add a CARCLFINI to an array that contains it', () => {
          const cARCLFINI: ICARCLFINI = { id: 123 };
          const cARCLFINICollection: ICARCLFINI[] = [
            {
              ...cARCLFINI,
            },
            { id: 456 },
          ];
          expectedResult = service.addCARCLFINIToCollectionIfMissing(cARCLFINICollection, cARCLFINI);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a CARCLFINI to an array that doesn't contain it", () => {
          const cARCLFINI: ICARCLFINI = { id: 123 };
          const cARCLFINICollection: ICARCLFINI[] = [{ id: 456 }];
          expectedResult = service.addCARCLFINIToCollectionIfMissing(cARCLFINICollection, cARCLFINI);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(cARCLFINI);
        });

        it('should add only unique CARCLFINI to an array', () => {
          const cARCLFINIArray: ICARCLFINI[] = [{ id: 123 }, { id: 456 }, { id: 30991 }];
          const cARCLFINICollection: ICARCLFINI[] = [{ id: 123 }];
          expectedResult = service.addCARCLFINIToCollectionIfMissing(cARCLFINICollection, ...cARCLFINIArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const cARCLFINI: ICARCLFINI = { id: 123 };
          const cARCLFINI2: ICARCLFINI = { id: 456 };
          expectedResult = service.addCARCLFINIToCollectionIfMissing([], cARCLFINI, cARCLFINI2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(cARCLFINI);
          expect(expectedResult).toContain(cARCLFINI2);
        });

        it('should accept null and undefined values', () => {
          const cARCLFINI: ICARCLFINI = { id: 123 };
          expectedResult = service.addCARCLFINIToCollectionIfMissing([], null, cARCLFINI, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(cARCLFINI);
        });

        it('should return initial array if no CARCLFINI is added', () => {
          const cARCLFINICollection: ICARCLFINI[] = [{ id: 123 }];
          expectedResult = service.addCARCLFINIToCollectionIfMissing(cARCLFINICollection, undefined, null);
          expect(expectedResult).toEqual(cARCLFINICollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
