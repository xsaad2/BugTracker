import { Component, Inject, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Project } from 'src/app/models/project';
import { User } from 'src/app/models/user';
import { ProjectService } from 'src/app/services/project.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-dev-assignment',
  templateUrl: './dev-assignment.component.html',
  styleUrls: ['./dev-assignment.component.css'],
})
export class DevAssignmentComponent implements OnInit {
  users: User[] = [];
  members: User[] = [];
  selectedDeveloper!: any;
  devAssignForm!: FormGroup;
  @Input('inputProject') inputProject!: Project;

  constructor(
    private projectService: ProjectService,
    private userService: UserService,
    private formBuilder: FormBuilder,
    private dialog: MatDialog,
    private _snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public project: Project
  ) {}

  ngOnInit(): void {
    this.userService.getUsers().subscribe((res) => {
      this.users = res.filter((user) => {
        return !this.project.members.some((member) => member.id === user.id);
      });
    });

    this.devAssignForm = this.formBuilder.group({
      member: [],
      user: [Validators.required],
    });
    this.devAssignForm.get('member')?.valueChanges.subscribe((value: User) => {
      this.selectedDeveloper = value;
    });
    this.devAssignForm.get('user')?.valueChanges.subscribe((value: User) => {
      this.selectedDeveloper = value;
    });
  }

  addDev() {
    console.log('selected: ' + this.selectedDeveloper);
    console.log(this.users);
    this.selectedDeveloper.forEach((developer: User) => {
      this.members.push(developer);
    });
    //this.members.push(this.selectedDeveloper);
    this.users = this.users.filter(
      (user) => !this.selectedDeveloper.includes(user)
    );

    console.log(this.members);
  }
  removeDev() {
    this.selectedDeveloper.forEach((developer: User) => {
      this.users.push(developer);
    });
    //this.members.push(this.selectedDeveloper);
    this.members = this.members.filter(
      (member) => !this.selectedDeveloper.includes(member)
    );
  }
  onSubmit(event: Event) {
    const body = this.selectedDeveloper.map((member: { id: any }) => {
      return member.id;
    });
    this.projectService.addMembersToProject(this.project.id, body).subscribe(
      (response) => {
        this._snackBar.open('members added successfully', 'OK', {
          duration: 5000,
          panelClass: ['success-snackbar'],
        });
      },
      (error) => {
        this._snackBar.open('members addition failed', 'OK', {
          duration: 5000,
          panelClass: ['failure-snackbar'],
        });
      }
    );
    this.dialog.closeAll();
  }
}
