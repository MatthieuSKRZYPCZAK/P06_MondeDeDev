import {environment} from '../../../environments/environment';

export const ApiRoutes = {
  auth: {
    login: `${environment.apiBaseUrl}/auth/login`,
    register: `${environment.apiBaseUrl}/auth/register`,
    me: `${environment.apiBaseUrl}/auth/me`,
    refresh: `${environment.apiBaseUrl}/auth/refresh`,
  },
  user: {
    update: `${environment.apiBaseUrl}/user/update`,
    changePassword: `${environment.apiBaseUrl}/user/password`,
    subscribe: (topicName: string) => `${environment.apiBaseUrl}/user/subscribe/${topicName}`,
    unsubscribe: (topicName: string) => `${environment.apiBaseUrl}/user/unsubscribe/${topicName}`,
  },
  topics: `${environment.apiBaseUrl}/topics`,
  posts: {
    base: `${environment.apiBaseUrl}/posts`,
    feed: `${environment.apiBaseUrl}/posts/feed`,
    detail: (postId: number) => `${environment.apiBaseUrl}/posts/${postId}`,
    comment: (postId: number) => `${environment.apiBaseUrl}/posts/${postId}/comment`,
  }
}
