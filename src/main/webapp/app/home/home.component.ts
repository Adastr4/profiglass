import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { HomeService } from 'app/home/home.service';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit, OnDestroy {
  account: Account | null = null;

  query1: any = {};
  query2: any = {};
  selectedDeviceObj = '';
  selectedDeviceObj2 = '';

  private readonly destroy$ = new Subject<void>();

  constructor(private accountService: AccountService, private router: Router, private homeService: HomeService) {}

  onChangeObj(newObj: any): void {
    this.selectedDeviceObj = newObj;

    this.homeService.getTest2(newObj.opzione).subscribe(response => {
      this.query2 = response.body;
    });
  }

  onChangeObj2(newObj: any): void {
    this.selectedDeviceObj2 = newObj;

    this.homeService.getTest2(newObj[0].opzione).subscribe(response => {
      this.query2 = response.body;
    });
  }

  ngOnInit(): void {
    this.homeService.getTest().subscribe(response => {
      this.query1 = response.body;
    });

    this.accountService
      .getAuthenticationState()
      .pipe(takeUntil(this.destroy$))
      .subscribe(account => (this.account = account));
  }

  login(): void {
    this.router.navigate(['/login']);
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
