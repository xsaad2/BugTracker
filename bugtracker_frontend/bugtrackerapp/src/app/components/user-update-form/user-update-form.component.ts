import { Component, Inject, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user';
import { RegistrationService } from 'src/app/services/registration.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-user-update-form',
  templateUrl: './user-update-form.component.html',
  styleUrls: ['./user-update-form.component.css'],
})
export class UserUpdateFormComponent implements OnInit {
  userUpdateForm!: FormGroup;
  roles = ['USER', 'ADMIN', 'Manager'].filter(
    (role) => this.user.role !== role
  );

  constructor(
    private userService: UserService,
    private router: Router,
    private formBuilder: FormBuilder,
    private _snackBar: MatSnackBar,
    private dialog: MatDialog,
    @Inject(MAT_DIALOG_DATA) public user: User
  ) {}

  ngOnInit(): void {
    this.userUpdateForm = this.formBuilder.group({
      firstName: [this.user.firstName, Validators.required],
      lastName: [this.user.lastName, Validators.required],
      email: [this.user.email, Validators.required],
      role: [this.user.role, Validators.required],
    });
  }

  onSave() {
    const model: any = {
      id: this.user.id,
      firstName: this.userUpdateForm.value.firstName,
      lastName: this.userUpdateForm.value.lastName,
      email: this.userUpdateForm.value.email,
      role: this.userUpdateForm.value.role,
    };

    this.userService.updateUser(model).subscribe(
      (response) => {
        this._snackBar.open('User updated successfully', 'OK', {
          duration: 5000,
          panelClass: ['success-snackbar'],
        });
      },
      (error) => {
        this._snackBar.open('User update failed', 'OK', {
          duration: 5000,
          panelClass: ['failure-snackbar'],
        });
      }
    );
    this.dialog.closeAll();
  }
  onDiscard() {
    this.dialog.closeAll();
  }
}
