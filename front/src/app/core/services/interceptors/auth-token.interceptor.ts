import {HttpErrorResponse, HttpInterceptorFn} from '@angular/common/http';
import {inject} from '@angular/core';
import {AuthService} from '../auth/auth.service';
import {Router} from '@angular/router';
import {catchError, switchMap, throwError} from 'rxjs';
import {ApiRoutes} from '../../api/api-routes';


export const authTokenInterceptor: HttpInterceptorFn = (req, next) => {

  const authService = inject(AuthService);
  const router = inject(Router);
  const token = authService.getToken();

  let requestToSend = req;

  const excludedUrls = [ApiRoutes.auth.login, ApiRoutes.auth.register, ApiRoutes.auth.refresh];

  const shouldSkip = excludedUrls.some((url) => req.url.includes(url));
  if (shouldSkip) {
    return next(req);
  }

  if(token) {
      const headers = req.headers.set('Authorization', 'Bearer ' + token);
      requestToSend = req.clone({
        headers: headers
      });
  }
  return next(requestToSend).pipe(
    catchError((error: HttpErrorResponse) => {
      if(error.status === 401) {
        return authService.getRefreshToken().pipe(
          switchMap((userSession) => {
            const headers = req.headers.set('Authorization', 'Bearer ' + userSession.token);
            requestToSend = req.clone({
              headers: headers
            });
            return next(requestToSend);
          }),
          catchError((err) => {
            authService.logout();
            void router.navigate(['']);
            return throwError(() => error);
          })
        )
      }
      return throwError(() => error);
    })
  );

};
