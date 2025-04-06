import {Component, OnDestroy} from '@angular/core';
import {Post} from '../../interfaces/post.interface';
import {Subject, takeUntil} from 'rxjs';
import {ActivatedRoute, RouterLink} from '@angular/router';
import {PostService} from '../../services/post.service';
import {MatProgressSpinner} from '@angular/material/progress-spinner';
import {CommonModule, DatePipe} from '@angular/common';
import {MatIcon, MatIconRegistry} from '@angular/material/icon';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {MatInput} from '@angular/material/input';
import {DomSanitizer} from '@angular/platform-browser';
import {MessageService} from '../../../../core/services/message/message.service';
import {MESSAGES} from '../../../../core/messages/messages';

@Component({
  selector: 'app-details',
  standalone: true,
  imports: [
    CommonModule,
    MatProgressSpinner,
    DatePipe,
    MatIcon,
    RouterLink,
    FormsModule,
    MatInput,
    ReactiveFormsModule
  ],
  templateUrl: './details.component.html',
  styleUrl: './details.component.scss'
})
export class DetailsComponent implements OnDestroy {
  commentForm: FormGroup;
  post?: Post;
  isLoading = true;
  idPost?: number;
  private readonly destroy$ = new Subject<void>();

  constructor(
    private fb: FormBuilder,
    private messageService: MessageService,
    private matIconRegistry: MatIconRegistry,
    private domSanitizer: DomSanitizer,
    private route: ActivatedRoute,
    private postService: PostService,
  ) {
    this.commentForm = this.fb.group({
      content: [''],
    });
    this.matIconRegistry.addSvgIcon('paper-plane', this.domSanitizer.bypassSecurityTrustResourceUrl('assets/icons/paper-plane.svg'));
    const postId = this.route.snapshot.paramMap.get('id');

    if (postId) {
      this.idPost = Number(postId);
      this.postService.getPostById(this.idPost)
        .pipe(takeUntil(this.destroy$))
        .subscribe({
          next: (post) => {
            this.post = post;
            this.isLoading = false;
          },
          error: () => {
            // todo gérer une redirection vers une 404
            this.isLoading = false;
          }
        });
    } else {
      this.isLoading = false;
    }
  }

  onSubmit() {
    const comment = this.commentForm.value;
    if(!comment) {
      return;
    }
    const id = Number(this.idPost);
    this.postService.addComment(id, comment)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next:(comment) => {
          this.post?.comments.push(comment);
          this.commentForm.reset();
          this.messageService.showInfo(MESSAGES.COMMENT_SENT_SUCCESS);
        },
        error: () => {
          this.messageService.showError(MESSAGES.ERROR);
          this.isLoading = false;
        }
      });

  }

  get isCommentInvalid(): boolean {
    const content = this.commentForm.get('content')?.value || '';
    return !content.trim();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

}
