import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {CreatePostRequest, Post} from '../interfaces/post.interface';
import {Comment, CommentRequest} from '../interfaces/comment.interface';
import {ApiRoutes} from '../../../core/api/api-routes';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(
    private http: HttpClient,
  ) { }

  getFeed(): Observable<Post[]> {
    return this.http.get<Post[]>(ApiRoutes.posts.feed);
  }

  createPost(post: CreatePostRequest): Observable<Post> {
    return this.http.post<Post>(ApiRoutes.posts.base, post);
  }

  getPostById(postId: number): Observable<Post> {
    return this.http.get<Post>(ApiRoutes.posts.detail(postId));
  }

  addComment(postId: number, comment: CommentRequest): Observable<Comment>{
    return this.http.post<Comment>(ApiRoutes.posts.comment(postId), comment);
  }
}
