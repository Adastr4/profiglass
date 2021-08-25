import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IParameters, getParametersIdentifier } from '../parameters.model';

export type EntityResponseType = HttpResponse<IParameters>;
export type EntityArrayResponseType = HttpResponse<IParameters[]>;

@Injectable({ providedIn: 'root' })
export class ParametersService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/parameters');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(parameters: IParameters): Observable<EntityResponseType> {
    return this.http.post<IParameters>(this.resourceUrl, parameters, { observe: 'response' });
  }

  update(parameters: IParameters): Observable<EntityResponseType> {
    return this.http.put<IParameters>(`${this.resourceUrl}/${getParametersIdentifier(parameters) as number}`, parameters, {
      observe: 'response',
    });
  }

  partialUpdate(parameters: IParameters): Observable<EntityResponseType> {
    return this.http.patch<IParameters>(`${this.resourceUrl}/${getParametersIdentifier(parameters) as number}`, parameters, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IParameters>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IParameters[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addParametersToCollectionIfMissing(
    parametersCollection: IParameters[],
    ...parametersToCheck: (IParameters | null | undefined)[]
  ): IParameters[] {
    const parameters: IParameters[] = parametersToCheck.filter(isPresent);
    if (parameters.length > 0) {
      const parametersCollectionIdentifiers = parametersCollection.map(parametersItem => getParametersIdentifier(parametersItem)!);
      const parametersToAdd = parameters.filter(parametersItem => {
        const parametersIdentifier = getParametersIdentifier(parametersItem);
        if (parametersIdentifier == null || parametersCollectionIdentifiers.includes(parametersIdentifier)) {
          return false;
        }
        parametersCollectionIdentifiers.push(parametersIdentifier);
        return true;
      });
      return [...parametersToAdd, ...parametersCollection];
    }
    return parametersCollection;
  }
}
