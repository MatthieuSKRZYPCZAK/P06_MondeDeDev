import {Component, OnDestroy} from '@angular/core';
import {MatCard, MatCardContent, MatCardHeader, MatCardTitle} from "@angular/material/card";
import {User} from '../../features/auth/interfaces/user.interface';
import {Topic} from '../../features/topics/interfaces/topic.interface';
import {Subject, takeUntil} from 'rxjs';
import {AuthService} from '../../core/services/auth/auth.service';
import {TopicService} from '../../features/topics/services/topic.service';
import {MessageService} from '../../core/services/message/message.service';
import {MESSAGES} from '../../core/messages/messages';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-subscription',
  standalone: true,
  imports: [
    MatCard,
    MatCardContent,
    MatCardHeader,
    MatCardTitle,
    RouterLink
  ],
  templateUrl: './subscription.component.html',
  styleUrl: './subscription.component.scss'
})
export class SubscriptionComponent implements OnDestroy {
  user?: User;
  topics: Topic[] = [];
  private readonly destroy$ = new Subject<void>();

  constructor(
    private authService: AuthService,
    private topicService: TopicService,
    private messageService: MessageService,
  ) {

    this.topicService.getTopics()
      .pipe(takeUntil(this.destroy$))
      .subscribe(topics => {
        this.topics = topics;
      });

    this.authService.getCurrentUser()
      .pipe(takeUntil(this.destroy$))
      .subscribe(user => {
        this.user = user;
      });
  }

  get userTopics(): Topic[] {
    if (!this.user) return [];
    return this.topics.filter(topic => this.user?.topics.includes(topic.name));
  }

  unsubscribe(topic: Topic) {
    this.topicService.unsubscribeFromTopic(topic.name).subscribe((updatedUser: User) => {
      this.authService.updateUser(updatedUser);
      this.user = updatedUser;
      this.messageService.showInfo(MESSAGES.UNSUBSCRIBE_SUCCESS(topic.label));
    });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

}
