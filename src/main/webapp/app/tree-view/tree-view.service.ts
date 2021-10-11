import { Injectable } from '@angular/core';
import { TreeviewItem } from 'ngx-treeview';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';

export type EntityResponseType = HttpResponse<JSON>;

@Injectable({ providedIn: 'root' })
export class TreeViewService {
  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  getConfiguration(idLega: string, idStato: string, idFinitura: string): Observable<EntityResponseType> {
    return this.http.get<JSON>(this.applicationConfigService.getEndpointFor(`api/configuratore/${idLega}/${idStato}/${idFinitura}`), {
      observe: 'response',
    });
  }

  getClstatfsFinitura(idLega: string, idStato: string, idFinitura: string): Observable<EntityResponseType> {
    return this.http.get<JSON>(this.applicationConfigService.getEndpointFor(`api/clstatfsfinitura/${idLega}/${idStato}/${idFinitura}`), {
      observe: 'response',
    });
  }

  getConfigTreeView(testConfig: any): TreeviewItem[] {
    const caratteristica: any = [];
    if (testConfig.caratteristiche !== undefined) {
      testConfig.caratteristiche.forEach((car: any) => {
        const valCaratteristica: any = [];
        car.valori.forEach((val: any) => {
          const childVal = {
            text: `Opzione: ${String(val.opzione)} | Descrizione: ${String(val.descrizione)}`,
            value: car.caratteristicaOrder * 10,
            checked: false,
          };
          valCaratteristica.push(childVal);
        });
        const childCaratteristica = {
          text: `ID: ${String(car.caratteristicaId)} | Classe: ${String(car.classe)}`,
          value: car.caratteristicaOrder,
          children: valCaratteristica,
          checked: false,
        };
        caratteristica.push(childCaratteristica);
      });
    }

    return [
      new TreeviewItem({
        text: 'Configuratore',
        value: 9,
        children: [
          {
            text: 'Caratteristica',
            value: 911,
            children: caratteristica,
            checked: false,
          },
          {
            text: 'Ciclo',
            value: 911,
            children: [],
            checked: false,
          },
          {
            text: 'Distinta',
            value: 912,
            children: [],
            checked: false,
          },
        ],
        checked: false,
      }),
    ];
  }
}
