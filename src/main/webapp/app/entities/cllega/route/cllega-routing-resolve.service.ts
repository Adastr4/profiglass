import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICLLEGA, CLLEGA } from '../cllega.model';
import { CLLEGAService } from '../service/cllega.service';

@Injectable({ providedIn: 'root' })
export class CLLEGARoutingResolveService implements Resolve<ICLLEGA> {
  constructor(protected service: CLLEGAService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICLLEGA> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((cLLEGA: HttpResponse<CLLEGA>) => {
          if (cLLEGA.body) {
            return of(cLLEGA.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CLLEGA());
  }
}
