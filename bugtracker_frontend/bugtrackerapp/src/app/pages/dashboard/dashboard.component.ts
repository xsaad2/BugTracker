import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import { Ticket } from 'src/app/models/ticket';
import { ProjectService } from 'src/app/services/project.service';
import { TicketService } from 'src/app/services/ticket.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
})
export class DashboardComponent {
  ticket!: Ticket;

  constructor() {}
}
