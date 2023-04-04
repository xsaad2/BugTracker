import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { map, Observable } from 'rxjs';
import { AuthenticationService } from '../services/authentication.service';

@Injectable({
  providedIn: 'root',
})
export class AuthenticationGuard implements CanActivate {
  constructor(
    private authService: AuthenticationService,
    private router: Router
  ) {}

  canActivate(): Observable<boolean> {
    // mapping isLoggedIn():Observable to this function:
    return this.authService.loggedIn().pipe(
      map((state): boolean => {
        if (!state) {
          // go back to login, if user is not logged in
          void this.router.navigate(['login']);
        }

        return state;
      })
    );
  }
}
