import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { Ticket } from '../models/ticket';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root',
})
export class TicketService {
  private apiServerUrl =
    'http://bugtrackerapp-env.eba-hdk8hzii.eu-north-1.elasticbeanstalk.com';

  ticketsListeners: (() => void)[] = [];

  constructor(private http: HttpClient) {}

  subscribeToTicketsChange(callback: () => void) {
    this.ticketsListeners.push(callback);
  }

  emitTicketsChange(): void {
    this.ticketsListeners.forEach((callback): void => {
      callback();
    });
  }

  public addTicket(ticket: Ticket, projectId: string): Observable<Ticket> {
    return this.http
      .post<Ticket>(`${this.apiServerUrl}/ticket/${projectId}`, ticket)
      .pipe(
        tap(() => {
          this.emitTicketsChange();
        })
      );
  }
  public updateTicket(ticket: Ticket): Observable<Ticket> {
    return this.http.put<Ticket>(`${this.apiServerUrl}/ticket`, ticket);
  }
  public getTicketById(id: string): Observable<Ticket> {
    return this.http.get<Ticket>(`${this.apiServerUrl}/ticket/${id}`);
  }
  public getCurrentUserTickets(): Observable<Ticket[]> {
    return this.http.get<Ticket[]>(`${this.apiServerUrl}/tickets`);
  }
  public getTicketsByProjectId(projectId: string): Observable<Ticket[]> {
    return this.http.get<Ticket[]>(
      `${this.apiServerUrl}/tickets/by/project/${projectId}`
    );
  }
  public getTicketProjectTeamMembers(ticketId: string): Observable<User[]> {
    return this.http.get<User[]>(
      `${this.apiServerUrl}/ticket/project/members/${ticketId}`
    );
  }

  public addMemberToTicket(
    ticketId: string,
    memberId: number
  ): Observable<Ticket> {
    return this.http.put<Ticket>(
      `${this.apiServerUrl}/ticket/${ticketId}/${memberId}`,
      {}
    );
  }
  public addMembersToTicket(
    ticketId: string,
    membersIds: number[]
  ): Observable<Ticket> {
    return this.http
      .put<Ticket>(`${this.apiServerUrl}/ticket/${ticketId}`, membersIds)
      .pipe(
        tap(() => {
          this.emitTicketsChange();
        })
      );
  }

  public removeMemberFromTicket(
    ticketId: string,
    devId: number
  ): Observable<void> {
    return this.http
      .delete<void>(`${this.apiServerUrl}/ticket/${ticketId}/${devId}`)
      .pipe(
        tap(() => {
          this.emitTicketsChange();
        })
      );
  }
  public changeTicketStatus(
    ticketId: string,
    ticket: Ticket
  ): Observable<Ticket> {
    return this.http
      .put<Ticket>(`${this.apiServerUrl}/ticket/status/${ticketId}`, ticket)
      .pipe(
        tap(() => {
          this.emitTicketsChange();
        })
      );
  }

  public deleteTicket(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/ticket/${id}`);
  }
}
