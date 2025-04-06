import {Component, inject} from '@angular/core';
import {TopicService} from '../services/topic.service';
import {AsyncPipe} from '@angular/common';
import {MatCard, MatCardContent, MatCardHeader, MatCardTitle} from '@angular/material/card';
import {AuthService} from '../../../core/services/auth/auth.service';
import {User} from '../../auth/interfaces/user.interface';
import {MessageService} from '../../../core/services/message/message.service';
import {MESSAGES} from '../../../core/messages/messages';
import {Topic} from '../interfaces/topic.interface';


@Component({
  selector: 'app-topics',
  standalone: true,
  imports: [
    AsyncPipe,
    MatCardHeader,
    MatCard,
    MatCardContent,
    MatCardTitle,
  ],
  templateUrl: './topics.component.html',
  styleUrl: './topics.component.scss'
})

export class TopicsComponent {
  private topicService = inject(TopicService);
  private authService = inject(AuthService);
  private messageService = inject(MessageService);

  topics$ = this.topicService.getTopics();
  userSignal = this.authService.getUser();

  subscribe(topic: Topic) {
    this.topicService.subscribeToTopic(topic.name).subscribe((updatedUser: User) => {
      this.authService.updateUser(updatedUser);
      this.messageService.showInfo(MESSAGES.SUBSCRIBE_SUCCESS(topic.label));
    });
  }

  unsubscribe(topic: Topic) {
    this.topicService.unsubscribeFromTopic(topic.name).subscribe((updatedUser: User) => {
      this.authService.updateUser(updatedUser);
      this.messageService.showInfo(MESSAGES.UNSUBSCRIBE_SUCCESS(topic.label));
    });
  }

}
