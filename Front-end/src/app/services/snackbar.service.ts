import { HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {
  MatSnackBar,
  MatSnackBarHorizontalPosition,
  MatSnackBarVerticalPosition,
} from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root',
})
export class SnackbarService {
  horizontalPosition: MatSnackBarHorizontalPosition = 'center';
  verticalPosition: MatSnackBarVerticalPosition = 'top';
  durationInSeconds = 5;

  constructor(private snackBar: MatSnackBar) {}

  printErrorCustom(errorMessage: string) {
    this.snackBar.open(`${errorMessage}`, 'Cerrar', {
      horizontalPosition: this.horizontalPosition,
      verticalPosition: this.verticalPosition,
      duration: this.durationInSeconds * 1000,
      panelClass: ['red-snackbar', 'login-snackbar'],
    });
  }

  printErrorLogin(error: HttpErrorResponse) {
    const statusError: number = error.status;
    const errorMessage: string = error.error.error;
    if (statusError === 401) {
      this.snackBar.open(
        `Hubo un error al iniciar la sesión - ${errorMessage}`,
        'Cerrar',
        {
          horizontalPosition: this.horizontalPosition,
          verticalPosition: this.verticalPosition,
          duration: this.durationInSeconds * 1000,
          panelClass: ['red-snackbar', 'login-snackbar'],
        }
      );
    }
  }

  printBienvenida(message: string) {
    this.snackBar.open(message, 'Cerrar', {
      horizontalPosition: this.horizontalPosition,
      verticalPosition: this.verticalPosition,
      duration: this.durationInSeconds * 1000,
      panelClass: ['green-snackbar', 'login-snackbar'],
    });
  }
}
