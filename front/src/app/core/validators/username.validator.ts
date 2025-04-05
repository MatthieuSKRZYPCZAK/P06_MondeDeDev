import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export function usernameValidator(): ValidatorFn {
  const usernameRegex = /^(?!.*\d{6,})[a-zA-Z0-9]{3,20}$/;

  return (control: AbstractControl): ValidationErrors | null => {
    const value = control.value;
    if (!value) {
      return null;
    }
    return usernameRegex.test(value) ? null : { invalidUsername: true };
  };
}
