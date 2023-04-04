import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  public model: any = {};
  loginForm!: FormGroup;
  sessionId: string = '';
  wrongCredentials: boolean = false;

  constructor(
    private authService: AuthenticationService,
    private router: Router,
    private formBuilder: FormBuilder
  ) {}

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', [Validators.required]],
    });
  }

  onLoginClick() {
    this.model = {
      email: this.loginForm.value.username,
      password: this.loginForm.value.password,
    };

    this.authService.login(this.model).subscribe(
      (res: any) => {
        this.sessionId = res.sessionId;
        sessionStorage.setItem('token', this.sessionId);
        this.router.navigate(['dashboard']);
      },
      (err: any) => {
        this.wrongCredentials = true;
        console.log(
          'Authentication failed: ' + err.message + 'my status: ' + err.status
        );
      }
    );
  }
}
