import {ApplicationConfig, LOCALE_ID, provideZoneChangeDetection} from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import {provideHttpClient, withInterceptors} from '@angular/common/http';
import {authTokenInterceptor} from './core/services/interceptors/auth-token.interceptor';

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }), // Optimise le changement de détection
    provideRouter(routes),   // Routing Angular
    { provide: LOCALE_ID, useValue: 'fr-FR'},
    provideHttpClient(withInterceptors([authTokenInterceptor])),
    provideAnimationsAsync() // Angular Material animations
  ]
};
