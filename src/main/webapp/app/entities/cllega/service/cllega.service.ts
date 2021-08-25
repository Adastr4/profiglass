import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICLLEGA, getCLLEGAIdentifier } from '../cllega.model';

export type EntityResponseType = HttpResponse<ICLLEGA>;
export type EntityArrayResponseType = HttpResponse<ICLLEGA[]>;

@Injectable({ providedIn: 'root' })
export class CLLEGAService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/cllegas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(cLLEGA: ICLLEGA): Observable<EntityResponseType> {
    return this.http.post<ICLLEGA>(this.resourceUrl, cLLEGA, { observe: 'response' });
  }

  update(cLLEGA: ICLLEGA): Observable<EntityResponseType> {
    return this.http.put<ICLLEGA>(`${this.resourceUrl}/${getCLLEGAIdentifier(cLLEGA) as number}`, cLLEGA, { observe: 'response' });
  }

  partialUpdate(cLLEGA: ICLLEGA): Observable<EntityResponseType> {
    return this.http.patch<ICLLEGA>(`${this.resourceUrl}/${getCLLEGAIdentifier(cLLEGA) as number}`, cLLEGA, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICLLEGA>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICLLEGA[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCLLEGAToCollectionIfMissing(cLLEGACollection: ICLLEGA[], ...cLLEGASToCheck: (ICLLEGA | null | undefined)[]): ICLLEGA[] {
    const cLLEGAS: ICLLEGA[] = cLLEGASToCheck.filter(isPresent);
    if (cLLEGAS.length > 0) {
      const cLLEGACollectionIdentifiers = cLLEGACollection.map(cLLEGAItem => getCLLEGAIdentifier(cLLEGAItem)!);
      const cLLEGASToAdd = cLLEGAS.filter(cLLEGAItem => {
        const cLLEGAIdentifier = getCLLEGAIdentifier(cLLEGAItem);
        if (cLLEGAIdentifier == null || cLLEGACollectionIdentifiers.includes(cLLEGAIdentifier)) {
          return false;
        }
        cLLEGACollectionIdentifiers.push(cLLEGAIdentifier);
        return true;
      });
      return [...cLLEGASToAdd, ...cLLEGACollection];
    }
    return cLLEGACollection;
  }
}
