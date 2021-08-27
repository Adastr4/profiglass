import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICARCLFINI, CARCLFINI } from '../carclfini.model';
import { CARCLFINIService } from '../service/carclfini.service';

@Injectable({ providedIn: 'root' })
export class CARCLFINIRoutingResolveService implements Resolve<ICARCLFINI> {
  constructor(protected service: CARCLFINIService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICARCLFINI> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((cARCLFINI: HttpResponse<CARCLFINI>) => {
          if (cARCLFINI.body) {
            return of(cARCLFINI.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CARCLFINI());
  }
}
