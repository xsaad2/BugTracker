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
import { TicketService } from 'src/app/services/ticket.service';
import { TicketCreationFormComponent } from '../ticket-creation-form/ticket-creation-form.component';

@Component({
  selector: 'app-tickets-table',
  templateUrl: './tickets-table.component.html',
  styleUrls: ['./tickets-table.component.css'],
})
export class TicketsTableComponent implements OnInit {
  @Input('project') project!: Project;
  @Output() ticketIdEvent = new EventEmitter<string>();

  projectId!: string;
  tickets!: Ticket[];

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
    private ticketService: TicketService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      this.projectId = params['id'];
    });
    this.fetchTickets();
    this.ticketService.subscribeToTicketsChange((): void => {
      this.fetchTickets();
    });
  }
  public fetchTickets(): void {
    this.ticketService
      .getTicketsByProjectId(this.projectId)
      .subscribe((response): void => {
        this.datasource = new MatTableDataSource<Ticket>(response);
        setTimeout(() => (this.datasource.paginator = this.paginator));
      });
  }

  OnTicketClick(id: string) {
    this.ticketIdEvent.emit(id);
  }

  onNewTicketClick() {
    const dialogRef = this.dialog.open(TicketCreationFormComponent, {
      width: '500px',
      data: this.project,
      panelClass: 'dialog',
    });
  }
}
