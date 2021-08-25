import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICLSTATF, CLSTATF } from '../clstatf.model';
import { CLSTATFService } from '../service/clstatf.service';

@Injectable({ providedIn: 'root' })
export class CLSTATFRoutingResolveService implements Resolve<ICLSTATF> {
  constructor(protected service: CLSTATFService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICLSTATF> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((cLSTATF: HttpResponse<CLSTATF>) => {
          if (cLSTATF.body) {
            return of(cLSTATF.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CLSTATF());
  }
}
