import {User} from '../../auth/interfaces/user.interface';
import {Topic} from '../../topics/interfaces/topic.interface';
import {Comment} from '../../posts/interfaces/comment.interface';

export interface Post {
  id: number;
  title: string;
  content: string;
  topic: Topic;
  author: User;
  createdAt: Date;
  comments: Comment[];
}

export interface CreatePostRequest {
  title: string,
  content: string,
  topicName: string,
}
