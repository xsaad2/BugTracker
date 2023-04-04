import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import { Project } from 'src/app/models/project';
import { Ticket } from 'src/app/models/ticket';
import { ProjectService } from 'src/app/services/project.service';
import { TicketService } from 'src/app/services/ticket.service';

@Component({
  selector: 'app-tickets-page',
  templateUrl: './tickets-page.component.html',
  styleUrls: ['./tickets-page.component.css'],
})
export class TicketsPageComponent implements OnInit {
  projectId!: string;
  project!: Project;
  ticket!: Ticket;

  constructor(
    private route: ActivatedRoute,
    private projectService: ProjectService,
    private ticketService: TicketService
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      this.projectId = params['id'];
    });

    this.fetchProject();
    this.projectService.subscribeToProjectDevsChanges((): void => {
      this.fetchProject();
    });
    this.ticketService.subscribeToTicketsChange((): void => {
      this.ticketService
        .getTicketById(this.ticket.id)
        .subscribe((res) => (this.ticket = res));
    });
  }

  private fetchProject() {
    this.projectService.getProjectById(this.projectId).subscribe((res) => {
      this.project = res;
    });
  }
  receiveData(id: string) {
    this.ticketService.getTicketById(id).subscribe((res) => {
      this.ticket = res;
    });
  }
}
