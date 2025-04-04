import { Component } from '@angular/core';
import {MatIcon} from "@angular/material/icon";
import {MatInput} from "@angular/material/input";
import {MatProgressSpinner} from "@angular/material/progress-spinner";
import {NgIf} from "@angular/common";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {Router, RouterLink} from "@angular/router";
import {Subject} from 'rxjs';
import {AuthService} from '../../../core/services/auth/auth.service';
import {MatSnackBar} from '@angular/material/snack-bar';
import {MessageService} from '../../../core/services/message/message.service';

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
export class RegisterComponent {
  registerForm: FormGroup;
  isLoading = false;

  private readonly destroy$: Subject<void> = new Subject<void>();

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private snackBar: MatSnackBar,
    private messageService: MessageService,
  ) {
    this.registerForm = this.fb.group({
      username: ['', Validators.required],
      email: ['', Validators.required],
      password: ['', Validators.required],
    })
  }

  onSubmit() {
    if(this.registerForm.invalid) {
      this.registerForm.markAllAsTouched();
      return;
    }

    this.isLoading = true;

  }


}
