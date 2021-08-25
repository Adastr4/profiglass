import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICLSTATF, getCLSTATFIdentifier } from '../clstatf.model';

export type EntityResponseType = HttpResponse<ICLSTATF>;
export type EntityArrayResponseType = HttpResponse<ICLSTATF[]>;

@Injectable({ providedIn: 'root' })
export class CLSTATFService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/clstatfs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(cLSTATF: ICLSTATF): Observable<EntityResponseType> {
    return this.http.post<ICLSTATF>(this.resourceUrl, cLSTATF, { observe: 'response' });
  }

  update(cLSTATF: ICLSTATF): Observable<EntityResponseType> {
    return this.http.put<ICLSTATF>(`${this.resourceUrl}/${getCLSTATFIdentifier(cLSTATF) as number}`, cLSTATF, { observe: 'response' });
  }

  partialUpdate(cLSTATF: ICLSTATF): Observable<EntityResponseType> {
    return this.http.patch<ICLSTATF>(`${this.resourceUrl}/${getCLSTATFIdentifier(cLSTATF) as number}`, cLSTATF, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICLSTATF>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICLSTATF[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCLSTATFToCollectionIfMissing(cLSTATFCollection: ICLSTATF[], ...cLSTATFSToCheck: (ICLSTATF | null | undefined)[]): ICLSTATF[] {
    const cLSTATFS: ICLSTATF[] = cLSTATFSToCheck.filter(isPresent);
    if (cLSTATFS.length > 0) {
      const cLSTATFCollectionIdentifiers = cLSTATFCollection.map(cLSTATFItem => getCLSTATFIdentifier(cLSTATFItem)!);
      const cLSTATFSToAdd = cLSTATFS.filter(cLSTATFItem => {
        const cLSTATFIdentifier = getCLSTATFIdentifier(cLSTATFItem);
        if (cLSTATFIdentifier == null || cLSTATFCollectionIdentifiers.includes(cLSTATFIdentifier)) {
          return false;
        }
        cLSTATFCollectionIdentifiers.push(cLSTATFIdentifier);
        return true;
      });
      return [...cLSTATFSToAdd, ...cLSTATFCollection];
    }
    return cLSTATFCollection;
  }
}
