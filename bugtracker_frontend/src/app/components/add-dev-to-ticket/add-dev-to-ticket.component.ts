import { Component, Inject, Input, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Ticket } from 'src/app/models/ticket';
import { User } from 'src/app/models/user';
import { LoaderService } from 'src/app/services/loader.service';
import { TicketService } from 'src/app/services/ticket.service';

@Component({
  selector: 'app-add-dev-to-ticket',
  templateUrl: './add-dev-to-ticket.component.html',
  styleUrls: ['./add-dev-to-ticket.component.css'],
})
export class AddDevToTicketComponent implements OnInit {
  users: User[] = [];
  members: User[] = [];
  selectedDeveloper!: any;
  devAssignForm!: FormGroup;
  @Input('inputTicket') inputTicket!: Ticket;

  constructor(
    private ticketService: TicketService,
    private formBuilder: FormBuilder,
    private dialog: MatDialog,
    private _snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public ticket: Ticket,
    public loaderService: LoaderService
  ) {}

  ngOnInit(): void {
    this.fetchProjectMembers();

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

  private fetchProjectMembers() {
    this.ticketService
      .getTicketProjectTeamMembers(this.ticket.id)
      .subscribe((res) => {
        this.users = res.filter((user) => {
          return !this.ticket.assignees.some((member) => member.id === user.id);
        });
      });
  }

  onSubmit(event: Event) {
    const body = this.selectedDeveloper.map((member: { id: any }) => {
      return member.id;
    });
    this.ticketService.addMembersToTicket(this.ticket.id, body).subscribe(
      (response) => {
        this.onSuccess();
      },
      (error) => {
        this.onFailure();
      }
    );
  }

  private onFailure() {
    this._snackBar.open('Developer assigning failed', 'OK', {
      duration: 5000,
      panelClass: ['failure-snackbar'],
    });
    this.dialog.closeAll();
  }

  private onSuccess() {
    this._snackBar.open('Developer(s) assigned successfully', 'OK', {
      duration: 5000,
      panelClass: ['success-snackbar'],
    });
    this.dialog.closeAll();
  }
}
