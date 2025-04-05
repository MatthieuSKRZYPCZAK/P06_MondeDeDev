import {CanActivateFn, Router} from '@angular/router';
import {inject} from '@angular/core';
import {AuthService} from '../auth/auth.service';
import {catchError, map, of} from 'rxjs';

export const authGuard: CanActivateFn = (_route, _state) => {

  const authService = inject(AuthService);
  const router = inject(Router);

  return authService.isAuthenticated().pipe(
    map(isAuthenticated => {
      if (!isAuthenticated) {
        authService.logout();
        return router.createUrlTree(['/login']);
      }
      return isAuthenticated;
    }),
    catchError(() => {
      authService.logout();
      return of(router.createUrlTree(['/login']));
    })
  );

};
