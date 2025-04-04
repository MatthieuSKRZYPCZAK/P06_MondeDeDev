import {Component, inject} from '@angular/core';
import {TopicService} from '../services/topic.service';
import {AsyncPipe} from '@angular/common';
import {MatCard, MatCardContent, MatCardHeader, MatCardTitle} from '@angular/material/card';
import {AuthService} from '../../../core/services/auth/auth.service';


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

  topics$ = this.topicService.getTopics();
  userSignal = this.authService.getUser();

}
