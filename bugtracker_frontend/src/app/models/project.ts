import { Ticket } from './ticket';
import { User } from './user';

export interface Project {
  id: string;
  title: string;
  description: string;
  projectCreator: User | null;
  members: User[];
  tickets: Ticket[];
}
