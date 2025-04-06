import {Component, OnDestroy} from '@angular/core';
import {MatIcon} from "@angular/material/icon";
import {MatInput} from "@angular/material/input";
import {MatProgressSpinner} from "@angular/material/progress-spinner";
import {NgIf} from "@angular/common";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {Router, RouterLink} from "@angular/router";
import {finalize, Subject, takeUntil} from 'rxjs';
import {AuthService} from '../../../core/services/auth/auth.service';
import {MessageService} from '../../../core/services/message/message.service';
import { passwordValidator } from '../../../core/validators/password.validator';
import {RegisterRequest} from '../interfaces/registerRequest.interface';
import {MESSAGES} from '../../../core/messages/messages';
import {HttpErrorResponse} from '@angular/common/http';
import {usernameValidator} from '../../../core/validators/username.validator';

@Component({
  selector: 'app-register',
  standalone: true,
    imports: [
        MatIcon,
        MatInput,
        MatProgressSpinner,
        NgIf,
        ReactiveFormsModule,
        RouterLink

    ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent implements OnDestroy {
  registerForm: FormGroup;
  isLoading = false;
  isSubmitted = false;
  errorMessages: { [key: string]: string } = {};

  private readonly destroy$: Subject<void> = new Subject<void>();

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private messageService: MessageService,
  ) {
    this.registerForm = this.fb.group({
      username: ['', [Validators.required, usernameValidator()]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, passwordValidator()]],
    });
  }

  onSubmit() {
    this.isSubmitted = true;
    this.updateErrorMessages();

    if(this.registerForm.valid) {
      this.isLoading = true;
      const payload: RegisterRequest = this.registerForm.value;

      this.authService.register(payload)
        .pipe(
          takeUntil(this.destroy$),
          finalize(() => this.isLoading = false),
        )
        .subscribe({
          next: () => {
            void this.router.navigate(['/feed']);
            this.messageService.showInfo(MESSAGES.REGISTER_SUCCESS);
          },
          error: (err: HttpErrorResponse) => {
            this.handleError(err);
            this.updateErrorMessages();
          }
        })
    }
  }

  private handleError(err: HttpErrorResponse) {
    if(err.status === 500 || err.status === 0) {
      this.messageService.showError(MESSAGES.SERVICE_UNAVAILABLE);
    } else if(err.status === 409 && err.error.error.includes('Email')) {
      this.registerForm.get('email')?.setErrors({ alreadyExists: true });
    } else if(err.status === 409 && err.error.error.includes('Username')) {
      this.registerForm.get('username')?.setErrors({ alreadyExists: true });
    } else {
      this.messageService.showError(MESSAGES.ERROR)
    }
  }

  private updateErrorMessages() {
    this.errorMessages = {}; // Reset à chaque soumission

    const usernameCtrl = this.registerForm.get('username');
    if (usernameCtrl?.errors) {
      if (usernameCtrl.hasError('required')) {
        this.errorMessages['username'] = "Le nom d'utilisateur est requis.";
      } else if (usernameCtrl.hasError('alreadyExists')) {
        this.errorMessages['username'] = "Ce nom d'utilisateur est déjà utilisé.";
      } else if (usernameCtrl.hasError('invalidUsername')) {
        this.errorMessages['username'] = "Le nom d'utilisateur doit contenir entre 3 et 20 caractères, uniquement lettres et chiffres.";
      }
    }

    const emailCtrl = this.registerForm.get('email');
    if (emailCtrl?.errors) {
      if (emailCtrl.hasError('required')) {
        this.errorMessages['email'] = "L'adresse e-mail est requise.";
      } else if (emailCtrl.hasError('alreadyExists')) {
        this.errorMessages['email'] = "Cette adresse e-mail est déjà utilisée.";
      } else if (emailCtrl.hasError('email')) {
        this.errorMessages['email'] = "Le format de l'adresse e-mail est invalide.";
      }
    }

    const passwordCtrl = this.registerForm.get('password');
    if (passwordCtrl?.errors) {
      if (passwordCtrl.hasError('required')) {
        this.errorMessages['password'] = "Le mot de passe est requis.";
      } else if (passwordCtrl.hasError('invalidPassword')) {
        this.errorMessages['password'] = "Le mot de passe doit avoir au moins 8 caractères, une majuscule, une minuscule, un chiffre et un caractère spécial.";
      }
    }
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }


}
