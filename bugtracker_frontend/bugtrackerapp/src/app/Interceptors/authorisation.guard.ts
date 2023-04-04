import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { Observable, map } from 'rxjs';
import { AuthenticationService } from '../services/authentication.service';

@Injectable({
  providedIn: 'root',
})
export class AdminGuard implements CanActivate {
  constructor(
    private authService: AuthenticationService,
    private router: Router
  ) {}

  canActivate(): Observable<boolean> {
    // mapping isAdmin():Observable to this function:
    return this.authService.isAdmin().pipe(
      map((state): boolean => {
        if (!state) {
          // go back to dashboard, if user is not an admin
          void this.router.navigate(['dashboard']);
        }
        return state;
      })
    );
  }
}
