import {Component, inject, OnDestroy} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {MatIcon} from '@angular/material/icon';
import {MatInput} from '@angular/material/input';
import {MatProgressSpinner} from '@angular/material/progress-spinner';
import {NgIf} from '@angular/common';
import {Router, RouterLink} from '@angular/router';
import {
  MatAutocomplete,
  MatAutocompleteTrigger,
  MatOption
} from '@angular/material/autocomplete';
import {TopicService} from '../../../topics/services/topic.service';
import {finalize, map, startWith, Subject, takeUntil} from 'rxjs';
import {CreatePostRequest} from '../../interfaces/post.interface';
import {PostService} from '../../services/post.service';
import {MessageService} from '../../../../core/services/message/message.service';
import {MESSAGES} from '../../../../core/messages/messages';
import {HttpErrorResponse} from '@angular/common/http';


@Component({
  selector: 'app-form',
  standalone: true,
  imports: [
    FormsModule,
    MatIcon,
    MatInput,
    MatProgressSpinner,
    NgIf,
    ReactiveFormsModule,
    RouterLink,
    MatAutocomplete,
    MatOption,
    MatAutocompleteTrigger,
  ],
  templateUrl: './form.component.html',
  styleUrl: './form.component.scss'
})
export class FormComponent implements OnDestroy {
  private topicService = inject(TopicService);
  private postService = inject(PostService);
  private messageService = inject(MessageService);
  private route = inject(Router);
  postForm: FormGroup;
  isLoading = false;
  isSubmitted = false;
  errorMessages: { [key: string]: string } = {};

  private readonly destroy$: Subject<void> = new Subject<void>();

  topics$ = this.topicService.getTopics();
  filteredTopics: { name: string; label: string }[] = [];

  constructor(
    private fb: FormBuilder,
  ) {
    this.postForm = this.fb.group({
      topicName: ['', [Validators.required]],
      title: ['', [Validators.required]],
      content: ['', [Validators.required]],
    });

    this.topics$.subscribe(topics => {
      this.filteredTopics = topics;

      this.postForm.get('topicName')?.valueChanges.pipe(
        startWith(''),
        map(value => this.filterTopics(value || '', topics))
      ).subscribe(filtered => {
        this.filteredTopics = filtered;
      });
    });
  }

  onSubmit() {
    this.isSubmitted = true;
    this.updateErrorMessages();
    if(this.postForm.valid) {
      this.isLoading = true;
      const selectedLabel = this.postForm.controls['topicName'].value;
      const selectedTopic = this.filteredTopics.find(topic => topic.label === selectedLabel);

      if(!selectedTopic) {
        this.messageService.showError(MESSAGES.THEME_INVALID);
        this.isLoading = false;
        return;
      }

      const payload: CreatePostRequest = {
        title: this.postForm.get('title')?.value,
        content: this.postForm.get('content')?.value,
        topicName: selectedTopic?.name,
      }


      this.postService.createPost(payload).pipe(
        takeUntil(this.destroy$),
        finalize(() => this.isLoading = false)
      )
        .subscribe({
          next: (post) => {
            this.messageService.showInfo(MESSAGES.POST_CREATED_SUCCESS);
            void this.route.navigate(['/post', post.id]);
          },
          error: (error: HttpErrorResponse) => {
            this.handleError(error);
            this.updateErrorMessages();
          }
        })

    }

  }

  private filterTopics(value: string, topics: { name: string; label: string }[]): { name: string; label: string }[] {
    const filterValue = value.toLowerCase();
    return topics.filter(topic => topic.label.toLowerCase().includes(filterValue));
  }

  private handleError(err: HttpErrorResponse) {
    if (err.status === 500 || err.status === 0) {
      this.messageService.showError(MESSAGES.SERVICE_UNAVAILABLE);
    } else {
      this.messageService.showError(MESSAGES.ERROR);
    }
  }

  private updateErrorMessages() {
    this.errorMessages = {};

    const topicCtrl = this.postForm.get('topicName');
    if (topicCtrl?.errors) {
      if (topicCtrl.hasError('required')) {
        this.errorMessages['topic'] = MESSAGES.THEME_REQUIRED;
      }
    }

    const titleCtrl = this.postForm.get('title');
    if (titleCtrl?.errors) {
      if (titleCtrl.hasError('required')) {
        this.errorMessages['title'] = MESSAGES.TITLE_REQUIRED;
      }
    }

    const contentCtrl = this.postForm.get('content');
    if (contentCtrl?.errors) {
      if (contentCtrl.hasError('required')) {
        this.errorMessages['content'] = MESSAGES.CONTENT_REQUIRED;
      }
    }
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
