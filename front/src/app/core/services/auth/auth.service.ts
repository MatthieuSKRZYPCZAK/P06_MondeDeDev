import {Injectable, Signal, signal} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {LoginRequest} from '../../../features/auth/interfaces/loginRequest.interface';
import {UserSessionInformation} from '../../../features/auth/interfaces/userSessionInformation.interface';
import {catchError, map, Observable, of, switchMap, tap, throwError} from 'rxjs';
import {ApiRoutes} from '../../api/api-routes';
import {User} from '../../../features/auth/interfaces/user.interface';
import {Router} from '@angular/router';
import {RegisterRequest} from '../../../features/auth/interfaces/registerRequest.interface';
import {UserUpdateRequest} from '../../../features/me/interfaces/userUpdateRequest.interface';
import {UserUpdatePasswordRequest} from '../../../features/me/interfaces/userUpdatePasswordRequest.interface';
import {ResponseDTO} from '../../interfaces/responseDTO.interface';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private LOGIN_URL = ApiRoutes.auth.login;
  private ME_URL = ApiRoutes.auth.me;
  private userSignal = signal<User | null>(null);

  constructor(
    private http: HttpClient,
    private router: Router,
  ) {}

  login(credentials: LoginRequest): Observable<UserSessionInformation> {
    return this.http.post<UserSessionInformation>(this.LOGIN_URL, credentials, { withCredentials: true })
      .pipe(
        tap(response => {
          this.saveToken(response.token);
          this.userSignal.set(response.user);
        })
      );
  }

  register(payload: RegisterRequest){
    return this.http.post<UserSessionInformation>(ApiRoutes.auth.register, payload, { withCredentials: true })
      .pipe(
        tap((response: UserSessionInformation) => {
          this.saveToken(response.token);
          this.userSignal.set(response.user);
        })
      );
  }

  saveToken(token: string): void {
    localStorage.setItem('token', token);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  getRefreshToken(): Observable<UserSessionInformation> {
    return this.http.post<UserSessionInformation>(ApiRoutes.auth.refresh, {}, { withCredentials: true })
    .pipe(
      tap((userSession: UserSessionInformation) => {
        this.saveToken(userSession.token);
        this.userSignal.set(userSession.user);
      })
    );
  }

  isAuthenticated(): Observable<boolean> {
    const token = this.getToken();

    if (!token) {
      return of(false);
    }

    if (this.userSignal()) {
      return of(true);
    }

    return this.getCurrentUser().pipe(
      map(() => true),
      catchError(() => {
        this.logout();
        return of(false);
      })
    );
  }

  getCurrentUser(): Observable<User> {
    return this.http.get<User>(ApiRoutes.auth.me)
      .pipe(
        tap(user => this.userSignal.set(user)),
        catchError(error => {
          if(error.status === 401) {
            return this.handleUnauthorizedError();
          }
          return throwError(() =>error);
        })
      );
  }

  getUser(): Signal<User | null> {
    return this.userSignal.asReadonly();
  }

  updateUser(user: User) {
    this.userSignal.set(user);
  }

  updateProfile(payload: UserUpdateRequest): Observable<User> {
    return this.http.put<User>(ApiRoutes.user.update, payload, { withCredentials: true })
      .pipe(
        tap(user => {
          this.userSignal.set(user);
        }),
        catchError(error => {
          if (error.status === 401) {
            return this.handleUnauthorizedError();
          }
          return throwError(() => error);
        })
      );
  }

  updatePassword(payload: UserUpdatePasswordRequest): Observable<ResponseDTO> {
    return this.http.patch<ResponseDTO>(ApiRoutes.user.changePassword, payload, { withCredentials: true });
  }


  logout(): void {
    localStorage.removeItem('token');
    this.userSignal.set(null);
    void this.router.navigate(['']);
  }

  private handleUnauthorizedError(): Observable<User> {
    return this.getRefreshToken().pipe(
      switchMap(() => {
        return this.http.get<User>(this.ME_URL).pipe(
          tap(user => this.userSignal.set(user))
        );
      }),
      catchError(refreshError => {
        this.logout();
        return throwError(() => refreshError);
      })
    );
  }
}
