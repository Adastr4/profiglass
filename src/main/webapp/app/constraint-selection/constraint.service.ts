import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { ICLLEGA } from 'app/entities/cllega/cllega.model';
import { ICLSTATF } from 'app/entities/clstatf/clstatf.model';

export type EntityResponseType = HttpResponse<ICLLEGA>;

@Injectable({ providedIn: 'root' })
export class ConstraintService {
  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  getCllegas(): Observable<EntityResponseType> {
    return this.http.get<ICLLEGA>(this.applicationConfigService.getEndpointFor(`api/cllegas`), { observe: 'response' });
  }

  getClstatfslega(id: string): Observable<HttpResponse<ICLSTATF>> {
    return this.http.get<ICLSTATF>(this.applicationConfigService.getEndpointFor(`/api/clstatfslega/${id}`), { observe: 'response' });
  }

  getClstatfsFinitura(idLega: string, idFinitura: string): Observable<HttpResponse<ICLSTATF>> {
    return this.http.get<ICLSTATF>(this.applicationConfigService.getEndpointFor(`api/clstatfsfinitura/${idLega} /${idFinitura}`), {
      observe: 'response',
    });
  }
}
