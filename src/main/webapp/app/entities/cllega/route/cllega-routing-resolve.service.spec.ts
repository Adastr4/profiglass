jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ICLLEGA, CLLEGA } from '../cllega.model';
import { CLLEGAService } from '../service/cllega.service';

import { CLLEGARoutingResolveService } from './cllega-routing-resolve.service';

describe('Service Tests', () => {
  describe('CLLEGA routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: CLLEGARoutingResolveService;
    let service: CLLEGAService;
    let resultCLLEGA: ICLLEGA | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(CLLEGARoutingResolveService);
      service = TestBed.inject(CLLEGAService);
      resultCLLEGA = undefined;
    });

    describe('resolve', () => {
      it('should return ICLLEGA returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCLLEGA = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCLLEGA).toEqual({ id: 123 });
      });

      it('should return new ICLLEGA if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCLLEGA = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultCLLEGA).toEqual(new CLLEGA());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as CLLEGA })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCLLEGA = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCLLEGA).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
