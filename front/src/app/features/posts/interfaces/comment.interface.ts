import {User} from '../../auth/interfaces/user.interface';

export interface Comment {
  id: number;
  content: string;
  author: User;
  createdAt: Date;
}
