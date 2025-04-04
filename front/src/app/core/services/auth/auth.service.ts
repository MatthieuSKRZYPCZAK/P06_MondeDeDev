import {computed, inject, Injectable, Signal, signal} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {LoginRequest} from '../../../features/auth/interfaces/loginRequest.interface';
import {UserSessionInformation} from '../../../features/auth/interfaces/userSessionInformation.interface';
import {map, Observable, Subject, tap} from 'rxjs';
import {ApiRoutes} from '../../api/api-routes';
import {User} from '../../../features/auth/interfaces/user.interface';
import {Router} from '@angular/router';
import {toObservable} from '@angular/core/rxjs-interop';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private LOGIN_URL = ApiRoutes.auth.login;
  private ME_URL = ApiRoutes.auth.me;
  private userSignal = signal<User | null>(null);

  constructor(private http: HttpClient) {}

  login(credentials: LoginRequest): Observable<UserSessionInformation> {
    return this.http.post<UserSessionInformation>(this.LOGIN_URL, credentials, { withCredentials: true })
      .pipe(
        tap(response => {
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

  isAuthenticated$: Observable<boolean> = toObservable(
    computed(() => !!this.userSignal())
  );

  getUser(): Signal<User | null> {
    return this.userSignal.asReadonly();
  }

  getCurrentUser(): Observable<User> {
    // return this.userSignal();
    const token = this.getToken();
    if(!token) {
      throw new Error('No token found.');
    }
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    return this.http.get<User>(this.ME_URL, { headers })
      .pipe(
        tap(user => this.userSignal.set(user))
      );
  }

  logout(): void {
    localStorage.removeItem('token');
    this.userSignal.set(null);
  }
}
