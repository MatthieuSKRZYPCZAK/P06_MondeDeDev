import {CanActivateFn, Router} from '@angular/router';
import {inject} from '@angular/core';
import {AuthService} from '../auth/auth.service';
import {map} from 'rxjs';

export const guestGuard: CanActivateFn = (_route, _state) => {

  const authService = inject(AuthService);
  const router = inject(Router);

  return authService.isAuthenticated().pipe(
    map((isAuthenticated) => {
      if (isAuthenticated) {
        return router.createUrlTree(['/feed']);
      }
      return true;
    })
  );
};
