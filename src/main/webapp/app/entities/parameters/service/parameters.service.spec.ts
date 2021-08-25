import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IParameters, Parameters } from '../parameters.model';

import { ParametersService } from './parameters.service';

describe('Service Tests', () => {
  describe('Parameters Service', () => {
    let service: ParametersService;
    let httpMock: HttpTestingController;
    let elemDefault: IParameters;
    let expectedResult: IParameters | IParameters[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ParametersService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        key: 'AAAAAAA',
        value: 'AAAAAAA',
        description: 'AAAAAAA',
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

      it('should create a Parameters', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Parameters()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Parameters', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            key: 'BBBBBB',
            value: 'BBBBBB',
            description: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Parameters', () => {
        const patchObject = Object.assign(
          {
            value: 'BBBBBB',
            description: 'BBBBBB',
          },
          new Parameters()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Parameters', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            key: 'BBBBBB',
            value: 'BBBBBB',
            description: 'BBBBBB',
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

      it('should delete a Parameters', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addParametersToCollectionIfMissing', () => {
        it('should add a Parameters to an empty array', () => {
          const parameters: IParameters = { id: 123 };
          expectedResult = service.addParametersToCollectionIfMissing([], parameters);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(parameters);
        });

        it('should not add a Parameters to an array that contains it', () => {
          const parameters: IParameters = { id: 123 };
          const parametersCollection: IParameters[] = [
            {
              ...parameters,
            },
            { id: 456 },
          ];
          expectedResult = service.addParametersToCollectionIfMissing(parametersCollection, parameters);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Parameters to an array that doesn't contain it", () => {
          const parameters: IParameters = { id: 123 };
          const parametersCollection: IParameters[] = [{ id: 456 }];
          expectedResult = service.addParametersToCollectionIfMissing(parametersCollection, parameters);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(parameters);
        });

        it('should add only unique Parameters to an array', () => {
          const parametersArray: IParameters[] = [{ id: 123 }, { id: 456 }, { id: 8371 }];
          const parametersCollection: IParameters[] = [{ id: 123 }];
          expectedResult = service.addParametersToCollectionIfMissing(parametersCollection, ...parametersArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const parameters: IParameters = { id: 123 };
          const parameters2: IParameters = { id: 456 };
          expectedResult = service.addParametersToCollectionIfMissing([], parameters, parameters2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(parameters);
          expect(expectedResult).toContain(parameters2);
        });

        it('should accept null and undefined values', () => {
          const parameters: IParameters = { id: 123 };
          expectedResult = service.addParametersToCollectionIfMissing([], null, parameters, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(parameters);
        });

        it('should return initial array if no Parameters is added', () => {
          const parametersCollection: IParameters[] = [{ id: 123 }];
          expectedResult = service.addParametersToCollectionIfMissing(parametersCollection, undefined, null);
          expect(expectedResult).toEqual(parametersCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
