import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IParameters, Parameters } from '../parameters.model';
import { ParametersService } from '../service/parameters.service';

@Injectable({ providedIn: 'root' })
export class ParametersRoutingResolveService implements Resolve<IParameters> {
  constructor(protected service: ParametersService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IParameters> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((parameters: HttpResponse<Parameters>) => {
          if (parameters.body) {
            return of(parameters.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Parameters());
  }
}
