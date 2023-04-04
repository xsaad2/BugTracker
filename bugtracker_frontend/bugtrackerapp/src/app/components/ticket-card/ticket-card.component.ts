import { Component, Input } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Ticket } from 'src/app/models/ticket';
import { User } from 'src/app/models/user';
import { TicketService } from 'src/app/services/ticket.service';
import { AddDevToTicketComponent } from '../add-dev-to-ticket/add-dev-to-ticket.component';

@Component({
  selector: 'app-ticket-card',
  templateUrl: './ticket-card.component.html',
  styleUrls: ['./ticket-card.component.css'],
})
export class TicketCardComponent {
  @Input('ticket') ticket!: Ticket;

  constructor(
    private ticketService: TicketService,
    private dialog: MatDialog
  ) {}

  onAddMembersClick() {
    const dialogRef = this.dialog.open(AddDevToTicketComponent, {
      width: '400px',
      data: this.ticket,
      panelClass: 'dialog',
    });
  }
  onRemoveDev(member: User) {
    this.ticketService
      .removeMemberFromTicket(this.ticket.id, member.id)
      .subscribe();
  }
  updateStatus(status: string) {
    const model: any = {
      id: this.ticket.id,
      title: this.ticket.title,
      description: this.ticket.description,
      status: status,
    };
    this.ticketService.changeTicketStatus(this.ticket.id, model).subscribe(
      (res) => console.log('success'),
      (err) => location.reload()
    );
  }
}
