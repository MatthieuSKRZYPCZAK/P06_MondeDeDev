import {CanActivateFn, Router} from '@angular/router';
import {inject} from '@angular/core';
import {AuthService} from '../auth/auth.service';
import {catchError, map, of, switchMap} from 'rxjs';

export const authGuard: CanActivateFn = (route, state) => {

  const authService = inject(AuthService);
  const router = inject(Router);

  return authService.isAuthenticated$.pipe(
    switchMap((isAuthenticated) => {
      if (isAuthenticated) {
        return of(true);
      } else {
        return authService.getRefreshToken().pipe(
          map(() => true),
          catchError(() => {
            authService.logout();
            return of(router.createUrlTree(['']));
          })
        );
      }
    })
  );

};
