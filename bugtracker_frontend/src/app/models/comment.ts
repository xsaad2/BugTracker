import { Ticket } from './ticket';
import { User } from './user';

export interface Comment {
  id: string;
  creationDate: Date;
  content: string;
  commentator: User;
  commentedTicket: Ticket;
}
