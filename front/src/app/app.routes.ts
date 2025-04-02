import { Routes } from '@angular/router';

export const routes: Routes = [


  { path: '',
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
        loadComponent: () =>
          import('./features/auth/login/login.component').then(m => m.LoginComponent),
      },
      {
        path: 'register',
        loadComponent: () =>
          import('./features/auth/register/register.component').then(m => m.RegisterComponent),
      },
      {
        path: 'me',
        loadComponent: () =>
          import('./features/me/components/me.component').then(m => m.MeComponent),
      },
      {
        path: 'feed',
        loadComponent: () =>
          import('./features/posts/components/feed/feed.component').then(m => m.FeedComponent),
      },
      {
        path: 'topics',
        loadComponent: () =>
          import('./features/topics/components/topics.component').then(m => m.TopicsComponent),
      }
    ]
  }
];
