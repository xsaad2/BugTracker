import { Component, OnInit } from '@angular/core';
import { Ticket } from 'src/app/models/ticket';
import { TicketService } from 'src/app/services/ticket.service';
import { Chart, registerables } from 'node_modules/chart.js';
Chart.register(...registerables);

@Component({
  selector: 'app-tickets-status-chart',
  templateUrl: './tickets-status-chart.component.html',
  styleUrls: ['./tickets-status-chart.component.css'],
})
export class TicketsStatusChartComponent implements OnInit {
  tickets!: Ticket[];
  chart: Chart | null = null;

  constructor(private ticketService: TicketService) {}

  ngOnInit(): void {
    this.fetchTickets();
    this.ticketService.subscribeToTicketsChange((): void => {
      this.fetchTickets();
    });
  }

  fetchTickets(): void {
    this.ticketService.getCurrentUserTickets().subscribe((response): void => {
      this.tickets = response;
      if (this.tickets) {
        this.RenderChart();
      }
    });
  }

  RenderChart() {
    const initialData = [0, 0]; // initial data array
    const data = this.tickets.reduce((acc, ticket) => {
      if (ticket.status === 'OPEN') {
        acc[0] += 1;
      } else if (ticket.status === 'CLOSED') {
        acc[1] += 1;
      }
      return acc;
    }, initialData);

    if (this.chart) {
      this.chart.data.datasets[0].data = data;
      this.chart.update();
    } else {
      this.chart = new Chart('piechart', {
        type: 'pie',
        data: {
          labels: ['Open', 'Closed'],
          datasets: [
            {
              label: '',
              data: data,
              borderWidth: 1,
            },
          ],
        },
        options: {
          scales: {
            y: {
              beginAtZero: true,
            },
          },
        },
      });
    }
  }
}
