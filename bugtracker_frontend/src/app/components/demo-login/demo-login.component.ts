import { Component } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-demo-login',
  templateUrl: './demo-login.component.html',
  styleUrls: ['./demo-login.component.css'],
})
export class DemoLoginComponent {
  constructor(
    private authService: AuthenticationService,
    private router: Router,
    private formBuilder: FormBuilder
  ) {}

  onAdminClick() {
    const model = {
      email: 'admin@mail.us',
      password: 'admin123',
    };

    this.authService.login(model).subscribe(
      (res: any) => {
        sessionStorage.setItem('token', res.sessionId);
        this.router.navigate(['dashboard']);
      },
      (err: any) => {
        console.log(
          'Authentication failed: ' + err.message + 'my status: ' + err.status
        );
      }
    );
  }
  onUserClick() {
    const model = {
      email: 'user@mail.us',
      password: 'user123',
    };

    this.authService.login(model).subscribe(
      (res: any) => {
        sessionStorage.setItem('token', res.sessionId);
        this.router.navigate(['dashboard']);
      },
      (err: any) => {
        console.log(
          'Authentication failed: ' + err.message + 'my status: ' + err.status
        );
      }
    );
  }
}
