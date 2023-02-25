import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse,
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { SnackbarService } from '../services/snackbar.service';

@Injectable()
export class GlobalErrorsInterceptor implements HttpInterceptor {
  constructor(private errorSnack: SnackbarService) {}

  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    return next
      .handle(request)
      .pipe(catchError((error) => this.errorHandler(error)));
  }

  errorHandler(error: HttpEvent<any>): Observable<HttpEvent<any>> {
    if (error instanceof HttpErrorResponse) {
      this.errorSnack.printErrorLogin(error);
    }
    throw error;
  }
}
