import {Component, OnDestroy} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule} from '@angular/forms';
import {Topic} from '../../topics/interfaces/topic.interface';
import {Subject, takeUntil} from 'rxjs';
import {TopicService} from '../../topics/services/topic.service';
import {AuthService} from '../../../core/services/auth/auth.service';
import {User} from '../../auth/interfaces/user.interface';
import {MESSAGES} from '../../../core/messages/messages';
import {MessageService} from '../../../core/services/message/message.service';
import {MatCard, MatCardContent, MatCardHeader, MatCardTitle} from '@angular/material/card';


@Component({
  selector: 'app-me',
  standalone: true,
  imports: [
    MatCard,
    MatCardHeader,
    MatCardContent,
    MatCardHeader,
    MatCardTitle,
    ReactiveFormsModule
  ],
  templateUrl: './me.component.html',
  styleUrl: './me.component.scss'
})
export class MeComponent implements OnDestroy {
  profileForm: FormGroup;
  user?: User;
  topics: Topic[] = [];
  private readonly destroy$ = new Subject<void>();


  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private topicService: TopicService,
    private messageService: MessageService,
  ) {
    this.profileForm = this.fb.group({
      username: [''],
      email: [''],
      password: [''],
    });

    this.topicService.getTopics()
      .pipe(takeUntil(this.destroy$))
      .subscribe(topics => {
        this.topics = topics;
      });

    this.authService.getCurrentUser()
      .pipe(takeUntil(this.destroy$))
      .subscribe(user => {
        this.user = user;
        this.profileForm.patchValue({
          username: user.username,
          email: user.email,
          password: '',
        });
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
}{


}
