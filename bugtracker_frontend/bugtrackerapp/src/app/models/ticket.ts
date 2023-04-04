import { Comment } from './comment';
import { Project } from './project';
import { User } from './user';

export interface Ticket {
  id: string;
  title: string;
  description: String;
  addedOn: Date;
  status: string;
  project: Project;
  ticketCreator: User;
  assignees: User[];
  ticketComments: Comment[];
}
