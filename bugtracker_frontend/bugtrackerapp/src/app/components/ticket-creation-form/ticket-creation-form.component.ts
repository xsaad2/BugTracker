import { Component, Inject, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Project } from 'src/app/models/project';
import { User } from 'src/app/models/user';
import { ProjectService } from 'src/app/services/project.service';
import { TicketService } from 'src/app/services/ticket.service';

@Component({
  selector: 'app-ticket-creation-form',
  templateUrl: './ticket-creation-form.component.html',
  styleUrls: ['./ticket-creation-form.component.css'],
})
export class TicketCreationFormComponent implements OnInit {
  user: any;
  ticketForm!: FormGroup;
  users: User[] = [];
  selectedDeveloper!: any;

  constructor(
    private projectService: ProjectService,
    private ticketService: TicketService,
    private formBuilder: FormBuilder,
    private dialog: MatDialog,
    private _snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public project: Project
  ) {}

  ngOnInit(): void {
    this.ticketForm = this.formBuilder.group({
      title: ['', Validators.required],
      description: ['', Validators.required],
      member: [],
    });
    this.projectService.getProjectMembers(this.project.id).subscribe((res) => {
      this.users = res;
    });
    this.ticketForm.get('member')?.valueChanges.subscribe((value: User) => {
      this.selectedDeveloper = value;
    });
  }

  onSubmit(event: Event) {
    event.preventDefault();
    let assigneesIds: number[] = [];
    if (this.selectedDeveloper) {
      this.selectedDeveloper.map((member: { id: number }) => {
        return member.id;
      });
    }

    const model: any = {
      title: this.ticketForm.value.title,
      description: this.ticketForm.value.description,
      assigneesIds: assigneesIds,
    };
    console.log(
      'model: ' +
        model.title +
        ' ' +
        model.assigneesIds +
        ' typeof: ' +
        typeof model.assigneesIds
    );
    this.ticketService.addTicket(model, this.project.id).subscribe(
      (response) => {
        this._snackBar.open('Ticket created successfully', 'OK', {
          duration: 5000,
          panelClass: ['success-snackbar'],
        });
      },
      (error) => {
        location.reload();
      }
    );
    this.dialog.closeAll();

    console.log('from Ticket creation component');
  }
}
