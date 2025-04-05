import {Injectable, Signal} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AuthService} from '../../../core/services/auth/auth.service';
import {map, Observable, switchMap} from 'rxjs';
import {Post} from '../interfaces/post.interface';
import {ApiRoutes} from '../../../core/api/api-routes';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(
    private http: HttpClient,
    private authService: AuthService,
  ) { }

  getFeed(): Observable<Post[]> {
    return this.http.get<Post[]>(ApiRoutes.posts.feed);
  }
}
