import { Component } from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {MatDialogActions, MatDialogContent, MatDialogRef} from '@angular/material/dialog';
import {MatFormField, MatLabel} from '@angular/material/form-field';
import {MatIcon} from '@angular/material/icon';
import {NgClass} from '@angular/common';

@Component({
  selector: 'app-confirm-password-dialog',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatDialogContent,
    MatFormField,
    MatLabel,
    MatDialogActions,
    MatIcon,
    NgClass
  ],
  templateUrl: './confirm-password-dialog.component.html',
  styleUrl: './confirm-password-dialog.component.scss'
})
export class ConfirmPasswordDialogComponent {
  form: FormGroup;

  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<ConfirmPasswordDialogComponent>
  ) {
    this.form = this.fb.group({
      password: ['', Validators.required]
    });
  }

  onConfirm() {
    if (this.form.valid) {
      this.dialogRef.close(this.form.value.password);
    }
  }

  onCancel() {
    this.dialogRef.close();
  }

}
