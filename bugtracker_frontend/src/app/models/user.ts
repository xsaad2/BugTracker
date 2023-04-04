import { Ticket } from './ticket';

export interface User {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  role: string;
  tickets: Ticket[];
}
