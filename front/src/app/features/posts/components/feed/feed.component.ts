import {Component, inject, signal} from '@angular/core';
import {AuthService} from '../../../../core/services/auth/auth.service';
import {Router, RouterLink} from '@angular/router';
import {PostService} from '../../services/post.service';
import {AsyncPipe, DatePipe, SlicePipe} from '@angular/common';
import {
  MatCard,
  MatCardActions,
  MatCardContent,
  MatCardHeader,
  MatCardSubtitle,
  MatCardTitle
} from '@angular/material/card';
import {map} from 'rxjs';
import {MatIcon} from '@angular/material/icon';


@Component({
  selector: 'app-feed',
  standalone: true,
  imports: [
    DatePipe,
    AsyncPipe,
    MatCard,
    MatCardHeader,
    MatCardContent,
    MatCardTitle,
    MatCardSubtitle,
    MatIcon,
    SlicePipe,
    RouterLink,
    MatCardActions,
  ],
  templateUrl: './feed.component.html',
  styleUrl: './feed.component.scss'
})
export class FeedComponent{

  private postService = inject(PostService);
  private authService = inject(AuthService);
  private router = inject(Router);

  userSignal = this.authService.getUser();

  sortDirection: 'asc' | 'desc' = 'desc';

  posts$ = this.postService.getFeed();

  toggleSort() {
    this.sortDirection = this.sortDirection === 'desc' ? 'asc' : 'desc';
    this.posts$ = this.postService.getFeed().pipe(
      map(posts => {
        if (this.sortDirection === 'desc') {
          return posts.sort((a, b) => new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime());
        } else {
          return posts.sort((a, b) => new Date(a.createdAt).getTime() - new Date(b.createdAt).getTime());
        }
      })
    );
  }

}
