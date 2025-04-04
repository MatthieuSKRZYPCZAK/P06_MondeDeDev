import { Routes } from '@angular/router';
import {authGuard} from './core/services/guards/auth.guard';
import {guestGuard} from './core/services/guards/guest.guard';

export const routes: Routes = [


  { path: '',
    canActivate: [guestGuard],
    loadComponent: () =>
      import('./features/home/home.component').then(m => m.HomeComponent),
  },
  {
    path: '',
    loadComponent: () =>
      import('./shared/main-layout/main-layout.component').then(m => m.MainLayoutComponent),
    children: [
      {
        path: 'login',
        canActivate: [guestGuard],
        loadComponent: () =>
          import('./features/auth/login/login.component').then(m => m.LoginComponent),
      },
      {
        path: 'register',
        canActivate: [guestGuard],
        loadComponent: () =>
          import('./features/auth/register/register.component').then(m => m.RegisterComponent),
      },
      {
        path: 'me',
        canActivate: [authGuard],
        loadComponent: () =>
          import('./features/me/components/me.component').then(m => m.MeComponent),
      },
      {
        path: 'feed',
        canActivate: [authGuard],
        loadComponent: () =>
          import('./features/posts/components/feed/feed.component').then(m => m.FeedComponent),
      },
      {
        path: 'topics',
        canActivate: [authGuard],
        loadComponent: () =>
          import('./features/topics/components/topics.component').then(m => m.TopicsComponent),
      }
    ]
  }
];
