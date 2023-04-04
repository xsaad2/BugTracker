import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { map, Observable, Observer, tap } from 'rxjs';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  private apiServerUrl =
    'http://bugtrackerapp-env.eba-hdk8hzii.eu-north-1.elasticbeanstalk.com';

  listeners: ((param: boolean) => void)[] = [];
  isAdminListeners: ((param: boolean) => void)[] = [];
  userIsAdmin: boolean = false;
  isLoggedIn: boolean = false;
  authPreCheck = false;

  constructor(private http: HttpClient, private router: Router) {}

  login(model: any): Observable<HttpResponse<any>> {
    let url = `${this.apiServerUrl}/login`;
    return this.http
      .post<any>(url, {
        username: model.email,
        password: model.password,
      })
      .pipe(
        tap((response) => {
          if (response.status === 200) {
            // if request was successful
            this.isLoggedIn = true; // set new stat
            this.emitLoginChange(true); // notify listeners
          }
        })
      );
  }
  logout(): Observable<HttpResponse<any>> {
    console.log('in logout');
    return this.http.delete<any>(`${this.apiServerUrl}/login`, {}).pipe(
      tap((response) => {
        // if request was successful

        //this.isLoggedIn = false; // set new stat
        sessionStorage.removeItem('token');
        this.router.navigate(['login']);
        this.emitLoginChange(false); // notify listeners
      })
    );
  }

  /**
   * returns the current login state
   */
  loggedIn(): Observable<boolean> {
    return this.checkLogIn().pipe(
      map((response: HttpResponse<boolean>): boolean => {
        this.emitLoginChange(response.body ?? false);
        return response.body ?? false;
      })
    );
  }
  checkLogIn(): Observable<HttpResponse<boolean>> {
    return this.http.get<boolean>(`${this.apiServerUrl}/isloggedin`, {
      observe: 'response',
    });
  }

  subscribeToLogin(callback: (param: boolean) => void) {
    this.listeners.push(callback);
  }

  emitLoginChange(newState: boolean): void {
    this.listeners.forEach((callback): void => {
      callback(newState);
    });
  }
  getCurrentUser(): Observable<HttpResponse<User>> {
    return this.http.get<User>(`${this.apiServerUrl}/currentuser`, {
      observe: 'response',
    });
  }

  subscribeToisAdmin(callback: (param: boolean) => void) {
    this.isAdminListeners.push(callback);
  }

  emitIsAdminChange(newState: boolean): void {
    this.isAdminListeners.forEach((callback): void => {
      callback(newState);
    });
  }

  isAdmin(): Observable<boolean> {
    return this.checkIsAdmin().pipe(
      map((response: HttpResponse<boolean>): boolean => {
        this.emitIsAdminChange(response.body ?? false);
        return response.body ?? false;
      })
    );
  }

  checkIsAdmin(): Observable<HttpResponse<boolean>> {
    return this.http.get<boolean>(`${this.apiServerUrl}/isAdmin`, {
      observe: 'response',
    });
  }
}
