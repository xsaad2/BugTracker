import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user';
import { UserService } from 'src/app/services/user.service';
import { UserUpdateFormComponent } from '../user-update-form/user-update-form.component';

@Component({
  selector: 'app-user-table',
  templateUrl: './user-table.component.html',
  styleUrls: ['./user-table.component.css'],
})
export class UserTableComponent implements OnInit {
  public users: User[] = [];
  displayedColumns: string[] = [];

  constructor(private userService: UserService, private dialog: MatDialog) {}

  ngOnInit(): void {
    this.getUsers();
    this.userService.subscribeToUsersChange((): void => {
      this.getUsers();
    });

    console.log('in ngoninit() of user-table' + this.users);
    this.displayedColumns = [
      'id',
      'firstName',
      'lastName',
      'email',
      'role',
      'actions',
    ];
  }

  public getUsers() {
    this.userService.getUsers().subscribe(
      (response: User[]) => {
        this.users = response;
      },
      (error: HttpErrorResponse) => {
        console.log(error);
      }
    );
  }
  public deleteUser(id: number) {
    this.userService.deleteUserById(id).subscribe();
    this.ngOnInit();
  }
  editUser(user: User) {
    const dialogRef = this.dialog.open(UserUpdateFormComponent, {
      width: '400px',
      data: user,
      panelClass: 'dialog',
    });
  }
}
