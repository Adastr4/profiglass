import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICARCLFINI, getCARCLFINIIdentifier } from '../carclfini.model';

export type EntityResponseType = HttpResponse<ICARCLFINI>;
export type EntityArrayResponseType = HttpResponse<ICARCLFINI[]>;

@Injectable({ providedIn: 'root' })
export class CARCLFINIService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/carclfinis');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(cARCLFINI: ICARCLFINI): Observable<EntityResponseType> {
    return this.http.post<ICARCLFINI>(this.resourceUrl, cARCLFINI, { observe: 'response' });
  }

  update(cARCLFINI: ICARCLFINI): Observable<EntityResponseType> {
    return this.http.put<ICARCLFINI>(`${this.resourceUrl}/${getCARCLFINIIdentifier(cARCLFINI) as number}`, cARCLFINI, {
      observe: 'response',
    });
  }

  partialUpdate(cARCLFINI: ICARCLFINI): Observable<EntityResponseType> {
    return this.http.patch<ICARCLFINI>(`${this.resourceUrl}/${getCARCLFINIIdentifier(cARCLFINI) as number}`, cARCLFINI, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICARCLFINI>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICARCLFINI[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCARCLFINIToCollectionIfMissing(
    cARCLFINICollection: ICARCLFINI[],
    ...cARCLFINISToCheck: (ICARCLFINI | null | undefined)[]
  ): ICARCLFINI[] {
    const cARCLFINIS: ICARCLFINI[] = cARCLFINISToCheck.filter(isPresent);
    if (cARCLFINIS.length > 0) {
      const cARCLFINICollectionIdentifiers = cARCLFINICollection.map(cARCLFINIItem => getCARCLFINIIdentifier(cARCLFINIItem)!);
      const cARCLFINISToAdd = cARCLFINIS.filter(cARCLFINIItem => {
        const cARCLFINIIdentifier = getCARCLFINIIdentifier(cARCLFINIItem);
        if (cARCLFINIIdentifier == null || cARCLFINICollectionIdentifiers.includes(cARCLFINIIdentifier)) {
          return false;
        }
        cARCLFINICollectionIdentifiers.push(cARCLFINIIdentifier);
        return true;
      });
      return [...cARCLFINISToAdd, ...cARCLFINICollection];
    }
    return cARCLFINICollection;
  }
}
