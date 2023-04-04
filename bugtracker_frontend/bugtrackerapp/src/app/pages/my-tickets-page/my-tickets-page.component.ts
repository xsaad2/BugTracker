import { Component, OnInit } from '@angular/core';
import { Ticket } from 'src/app/models/ticket';
import { TicketService } from 'src/app/services/ticket.service';

@Component({
  selector: 'app-my-tickets-page',
  templateUrl: './my-tickets-page.component.html',
  styleUrls: ['./my-tickets-page.component.css'],
})
export class MyTicketsPageComponent implements OnInit {
  ticket!: Ticket;

  constructor(private ticketService: TicketService) {}

  ngOnInit(): void {
    this.ticketService.subscribeToTicketsChange((): void => {
      this.ticketService
        .getTicketById(this.ticket.id)
        .subscribe((res) => (this.ticket = res));
    });
  }

  receiveData(id: string) {
    this.ticketService.getTicketById(id).subscribe((res) => {
      this.ticket = res;
    });
  }
}
