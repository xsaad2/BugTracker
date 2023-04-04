import { Component, OnInit } from '@angular/core';

import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { RegistrationService } from 'src/app/services/registration.service';
@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css'],
})
export class RegistrationComponent implements OnInit {
  registrationForm!: FormGroup;
  regFailed: boolean | undefined;
  emailExists!: boolean;

  constructor(
    private registrationService: RegistrationService,
    private router: Router,
    private formBuilder: FormBuilder,
    private dialog: MatDialog,
    private _snackBar: MatSnackBar
  ) {}
  ngOnInit(): void {
    this.registrationForm = this.formBuilder.group(
      {
        firstName: ['', Validators.required],
        lastName: ['', Validators.required],
        email: ['', Validators.required],
        password: ['', [Validators.required]],
        repeatPassword: ['', [Validators.required]],
      },
      {
        validators: [
          this.matchingPasswordsValidator('password', 'repeatPassword'),
        ],
      }
    );
  }
  matchingPasswordsValidator(passwordKey: string, repeatPasswordKey: string) {
    return (group: FormGroup) => {
      const password = group.controls[passwordKey];
      const repeatPassword = group.controls[repeatPasswordKey];

      if (password.value !== repeatPassword.value) {
        repeatPassword.setErrors({ mismatchedPasswords: true });
      } else {
        repeatPassword.setErrors(null);
      }
    };
  }

  async onRegister() {
    this.regFailed = false;
    this.emailExists = false;
    const model: any = {
      firstName: this.registrationForm.value.firstName,
      lastName: this.registrationForm.value.lastName,
      email: this.registrationForm.value.email,
      password: this.registrationForm.value.repeatPassword,
    };
    await this.registrationService.checkEmail(model.email).subscribe((res) => {
      this.emailExists = res;
    });

    this.registrationService.register(model).subscribe(
      (response) => {
        console.log('registration success: ' + response);
        this._snackBar.open(
          'Account created successfully, you can now log in',
          'OK',
          {
            duration: 5000,
            panelClass: ['success-snackbar'],
          }
        );
        this.router.navigate(['login']);
      },
      (error) => {
        if (this.emailExists) {
          console.log('email exists: ' + this.emailExists);
          return;
        }
        this.regFailed = true;
      }
    );
  }
}
