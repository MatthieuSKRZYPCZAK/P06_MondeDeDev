import {Component, OnDestroy} from '@angular/core';
import {MatIconModule} from '@angular/material/icon';
import {Router, RouterLink} from '@angular/router';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatButtonModule} from '@angular/material/button';
import {MatInputModule} from '@angular/material/input';
import {NgIf} from '@angular/common';
import {AuthService} from '../../../core/services/auth/auth.service';
import {MatSnackBar} from '@angular/material/snack-bar';
import {LoginRequest} from '../interfaces/loginRequest.interface';
import {HttpErrorResponse} from '@angular/common/http';
import {MatProgressSpinner} from '@angular/material/progress-spinner';
import {finalize, Subject, takeUntil} from 'rxjs';
import {MESSAGES} from '../../../core/messages/messages';
import {MessageService} from '../../../core/services/message/message.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    RouterLink,
    NgIf,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatProgressSpinner,
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent implements OnDestroy{
  loginForm: FormGroup;
  isLoading = false;
  private readonly destroy$: Subject<void> = new Subject<void>();

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private snackBar: MatSnackBar,
    private messageService: MessageService,
    ) {
    this.loginForm = this.fb.group({
      identifier: ['', Validators.required],
      password: ['', Validators.required],
    })
  }

  onSubmit() {
    if(this.loginForm.invalid) {
      this.loginForm.markAllAsTouched();
      return;
    }

    this.isLoading = true;
    const credentials: LoginRequest = this.loginForm.value;

    this.authService.login(credentials)
      .pipe(
        takeUntil(this.destroy$),
        finalize(() => this.isLoading = false),
      )
      .subscribe({
        next: (result) => {
          this.authService.saveToken(result.token);
          void this.router.navigate(['/feed']);
          this.messageService.showInfo(MESSAGES.LOGIN_SUCCESS);
        },
        error: (err: HttpErrorResponse) => {
          this.handleError(err);
        }
    });
  }

  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.complete();
  }

  private handleError(err: HttpErrorResponse) {
    if(err.status === 500 || err.status === 0) {
      this.messageService.showError(MESSAGES.AUTH.SERVICE_UNAVAILABLE);
    } else if(err.status === 401) {
      this.messageService.showError(MESSAGES.AUTH.INVALID_CREDENTIALS);
    } else {
      this.messageService.showError(MESSAGES.AUTH.DEFAULT)
    }
  }
}
