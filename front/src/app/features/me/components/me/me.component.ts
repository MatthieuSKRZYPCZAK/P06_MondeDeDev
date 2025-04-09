import {Component, OnDestroy} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {Topic} from '../../../topics/interfaces/topic.interface';
import {forkJoin, Subject, takeUntil} from 'rxjs';
import {AuthService} from '../../../../core/services/auth/auth.service';
import {User} from '../../../auth/interfaces/user.interface';
import {MessageService} from '../../../../core/services/message/message.service';
import {SubscriptionComponent} from '../../../../shared/subscription/subscription.component';
import {MatDivider} from '@angular/material/divider';
import {ConfirmPasswordDialogComponent} from '../confirm-password-dialog/confirm-password-dialog.component';
import {MatDialog} from '@angular/material/dialog';
import {LoginRequest} from '../../../auth/interfaces/loginRequest.interface';
import {UserUpdateRequest} from '../../interfaces/userUpdateRequest.interface';
import {UserUpdatePasswordRequest} from '../../interfaces/userUpdatePasswordRequest.interface';
import {passwordValidator} from '../../../../core/validators/password.validator';
import {usernameValidator} from '../../../../core/validators/username.validator';
import {NgIf} from '@angular/common';
import {HttpErrorResponse} from '@angular/common/http';
import {MESSAGES} from '../../../../core/messages/messages';


@Component({
  selector: 'app-me',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    SubscriptionComponent,
    MatDivider,
    NgIf
  ],
  templateUrl: './me.component.html',
  styleUrl: './me.component.scss'
})
export class MeComponent implements OnDestroy {
  profileForm: FormGroup;
  user?: User;
  topics: Topic[] = [];
  private readonly destroy$ = new Subject<void>();
  errorMessages: { [key: string]: string } = {};


  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private messageService: MessageService,
    private dialog: MatDialog,
  ) {
    this.profileForm = this.fb.group({
      username: ['', usernameValidator()],
      email: ['', Validators.email],
      newPassword: ['', passwordValidator()],
    });

    this.profileForm.valueChanges
      .pipe(takeUntil(this.destroy$))
      .subscribe(() => {
        this.updateErrorMessages();
      });


    this.authService.getCurrentUser()
      .pipe(takeUntil(this.destroy$))
      .subscribe(user => {
        this.user = user;
        this.profileForm.patchValue({
          username: user.username,
          email: user.email,
          newPassword: '',
        });
      });
  };

  get isFormModified(): boolean {
    if (!this.user) {
      return false;
    }

    const formData = this.profileForm.value;

    const usernameChanged = formData.username?.trim() !== this.user.username;
    const emailChanged = formData.email?.trim() !== this.user.email;
    const passwordFilled = formData.newPassword?.trim() !== '';

    return usernameChanged || emailChanged || passwordFilled;
  }

  validateForm(): boolean {
    this.errorMessages = {};

    const usernameControl = this.profileForm.get('username');
    const emailControl = this.profileForm.get('email');
    const passwordControl = this.profileForm.get('newPassword');

    if (usernameControl?.invalid && usernameControl.dirty) {
      this.updateErrorMessages();
    }

    if (emailControl?.invalid && emailControl.dirty) {
      this.updateErrorMessages();
    }

    if (passwordControl?.invalid && passwordControl?.dirty) {
      this.updateErrorMessages();
    }

    return Object.keys(this.errorMessages).length === 0;
  }

  onSave() {
    if (!this.validateForm()) {
      return;
    }
    const formData = this.profileForm.value;

    if (!formData.username?.trim()) {
      this.profileForm.patchValue({ username: this.user?.username });
    }
    if (!formData.email?.trim()) {
      this.profileForm.patchValue({ email: this.user?.email });
    }

    const correctedData = this.profileForm.value;

    const passwordTouched = correctedData.newPassword && correctedData.newPassword.trim() !== '';

    const dialogRef = this.dialog.open(ConfirmPasswordDialogComponent, {
      disableClose: false,
      data: { passwordChange: passwordTouched }
    });

    dialogRef.afterClosed().subscribe(password => {
      if (!password) {
        return;
      }

      const loginPayload: LoginRequest = {
        identifier: this.user?.email ?? '',
        password: password
      };

      this.authService.login(loginPayload)
        .pipe(takeUntil(this.destroy$))
        .subscribe({
          next: () => {
            const actions = [];
            const updateData: UserUpdateRequest = {
              username: correctedData.username,
              email: correctedData.email,
            };

            actions.push(
              this.authService.updateProfile(updateData)
            );

            if(passwordTouched) {
              const updatePassword: UserUpdatePasswordRequest = {
                oldPassword: password,
                newPassword: correctedData.newPassword
              };

              actions.push(
                this.authService.updatePassword(updatePassword)
              );
            }

            forkJoin(actions)
              .pipe(takeUntil(this.destroy$))
              .subscribe({
                next: () => {
                  this.messageService.showInfo(MESSAGES.PROFILE_UPDATE_SUCCESS);
                  this.profileForm.patchValue({ password: '' });

                  this.authService.getCurrentUser()
                    .pipe(takeUntil(this.destroy$))
                    .subscribe(user => {
                      this.user = user;
                      this.profileForm.patchValue({
                        username: user.username,
                        email: user.email,
                        newPassword: '',
                      });
                    });

                },
                error: (error) => {
                  this.handleError(error);
                  this.updateErrorMessages();
                }
              });
          },
          error: () => {
            this.messageService.showError(MESSAGES.PASSWORD_CONFIRMATION_ERROR);
          }
        });
    });
  }

  private handleError(err: HttpErrorResponse): boolean {
    if (err.status === 500 || err.status === 0) {
      this.messageService.showError(MESSAGES.SERVICE_UNAVAILABLE);
      return false;
    } else if (err.status === 409) {
      if (err.error.error.includes('Email')) {
        this.profileForm.get('email')?.setErrors({ alreadyExists: true });
      } else if (err.error.error.includes('Username')) {
        this.profileForm.get('username')?.setErrors({ alreadyExists: true });
      }
      return true;
    }
    return false;
  }

  private updateErrorMessages() {
    this.errorMessages = {};

    const usernameCtrl = this.profileForm.get('username');
    if (usernameCtrl?.errors) {
      if (usernameCtrl.hasError('alreadyExists')) {
        this.errorMessages['username'] = MESSAGES.USERNAME_ALREADY_EXISTS;
      } else if (usernameCtrl.hasError('invalidUsername')) {
        this.errorMessages['username'] = MESSAGES.USERNAME_INVALID;
      }
    }

    const emailCtrl = this.profileForm.get('email');
    if (emailCtrl?.errors) {
      if (emailCtrl.hasError('alreadyExists')) {
        this.errorMessages['email'] = MESSAGES.EMAIL_ALREADY_EXISTS;
      } else if (emailCtrl.hasError('email')) {
        this.errorMessages['email'] = MESSAGES.EMAIL_INVALID;
      }
    }

    const passwordCtrl = this.profileForm.get('newPassword');
    if (passwordCtrl?.errors) {
      if (passwordCtrl.hasError('invalidPassword')) {
        this.errorMessages['newPassword'] = MESSAGES.PASSWORD_INVALID;
      }
    }
  }


  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}{


}
