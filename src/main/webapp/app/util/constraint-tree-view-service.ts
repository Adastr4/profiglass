import { Injectable } from '@angular/core';
import { ReplaySubject } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class TransferDataService {
  data: any = {
    lega: '',
    stato: '',
    finitura: '',
  };

  subject = new ReplaySubject(this.data);
  test: any;

  constructor() {
    this.subject.subscribe(data => (this.test = data));
  }

  setLega(selectedDeviceCllegas: any): void {
    this.data.lega = selectedDeviceCllegas;
    this.changeNotification();
  }

  setStato(selectedDeviceClstatfslega: any): void {
    this.data.stato = selectedDeviceClstatfslega;
    this.changeNotification();
  }

  setFinitura(selectedDeviceClstatfsFinitura: any): void {
    this.data.finitura = selectedDeviceClstatfsFinitura;
    this.changeNotification();
  }

  changeNotification(): any {
    if (this.data.lega !== '' && this.data.stato !== '' && this.data.finitura !== '') {
      this.subject.next(this.data);
    }
  }
}
