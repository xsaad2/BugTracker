import {
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
  ViewChild,
} from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { Project } from 'src/app/models/project';
import { Ticket } from 'src/app/models/ticket';
import { ProjectService } from 'src/app/services/project.service';
import { TicketService } from 'src/app/services/ticket.service';
import { TicketCreationFormComponent } from '../ticket-creation-form/ticket-creation-form.component';

@Component({
  selector: 'app-my-tickets',
  templateUrl: './my-tickets.component.html',
  styleUrls: ['./my-tickets.component.css'],
})
export class MyTicketsComponent implements OnInit {
  @Output() ticketIdEvent = new EventEmitter<string>();

  datasource: any;
  displayedColumns = [
    'id',
    'CreationDate',
    'title',
    'description',
    'creator',
    'status',
  ];

  @ViewChild(MatPaginator) paginator: MatPaginator | undefined;

  constructor(
    private route: ActivatedRoute,
    private projectService: ProjectService,
    private ticketService: TicketService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.fetchTickets();
    this.ticketService.subscribeToTicketsChange((): void => {
      this.fetchTickets();
    });
  }
  public fetchTickets(): void {
    this.ticketService.getCurrentUserTickets().subscribe((response): void => {
      this.datasource = new MatTableDataSource<Ticket>(response);
      setTimeout(() => (this.datasource.paginator = this.paginator));
    });
  }

  OnTicketClick(id: string) {
    this.ticketIdEvent.emit(id);
  }
}
