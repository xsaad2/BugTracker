import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class LoaderService {
  constructor() {}

  public isLoading: BehaviorSubject<boolean> = new BehaviorSubject(false);
  setLoading(value: boolean): void {
    setTimeout(() => {
      this.isLoading.next(value);
    });
  }
}
