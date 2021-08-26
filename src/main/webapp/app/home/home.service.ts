import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { ICLLEGA } from 'app/entities/cllega/cllega.model';
import { ICLSTATF } from 'app/entities/clstatf/clstatf.model';

export type EntityResponseType = HttpResponse<ICLLEGA>;

@Injectable({ providedIn: 'root' })
export class HomeService {
  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  getTest(): Observable<EntityResponseType> {
    return this.http.get<ICLLEGA>(this.applicationConfigService.getEndpointFor(`api/cllegas`), { observe: 'response' });
  }

  getTest2(id: string): Observable<HttpResponse<ICLLEGA>> {
    return this.http.get<ICLLEGA>(this.applicationConfigService.getEndpointFor(`/api/clstatfslega/${id}`), { observe: 'response' });
  }
}
