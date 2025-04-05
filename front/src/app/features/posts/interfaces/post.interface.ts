import {User} from '../../auth/interfaces/user.interface';
import {Topic} from '../../topics/interfaces/topic.interface';

export interface Post {
  id: number;
  title: string;
  content: string;
  topic: Topic;
  author: User;
  createdAt: Date;
  comments: Comment[];
}
