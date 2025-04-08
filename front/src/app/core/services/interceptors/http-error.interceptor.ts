import {HttpErrorResponse, HttpInterceptorFn} from '@angular/common/http';
import {catchError, throwError} from 'rxjs';
import {inject} from '@angular/core';
import {MessageService} from '../message/message.service';
import {MESSAGES} from '../../messages/messages';

export const httpErrorInterceptor: HttpInterceptorFn = (req, next) => {

  const messageService = inject(MessageService);

  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      if (error.status === 500 || error.status === 0) {
        messageService.showError(MESSAGES.SERVICE_UNAVAILABLE);
      }
      return throwError(() => error);
    })
  );
};
