import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Topic} from '../interfaces/topic.interface';
import {Observable} from 'rxjs';
import {ApiRoutes} from '../../../core/api/api-routes';
import {User} from '../../auth/interfaces/user.interface';

@Injectable({
  providedIn: 'root'
})
export class TopicService {

  constructor(private http: HttpClient) {}

  getTopics(): Observable<Topic[]> {
    return this.http.get<Topic[]>(ApiRoutes.topics);
  }

  subscribeToTopic(topicName: string) {
    return this.http.post<User>(ApiRoutes.user.subscribe(topicName),{});
  }

  unsubscribeFromTopic(topicName: string) {
    return this.http.post<User>(ApiRoutes.user.unsubscribe(topicName),{});
  }
}
