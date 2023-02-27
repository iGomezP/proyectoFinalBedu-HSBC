import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { JwtService } from '../services/api/jwt.service';
import { Router } from '@angular/router';
import { SnackbarService } from '../services/snackbar.service';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
  // Lista de todas las peticiones http incluidas salidas al backend
  private listaBlancaUrls: string[] = [
    '/login',
    '/signup',
    '/productos',
    '/register',
    '/funkos',
  ];

  constructor(
    private tokenService: JwtService,
    private router: Router,
    private snackService: SnackbarService
  ) {}

  intercept(
    request: HttpRequest<unknown>,
    next: HttpHandler
  ): Observable<HttpEvent<unknown>> {
    // Verificar en la lista de Url
    if (this.listaBlancaUrls.some((url) => request.url.includes(url))) {
      return next.handle(request);
    }

    // Verificar si el usuario está autenticado
    if (!this.tokenService.getJwtToken) {
      this.tokenService.resetLocalStorage();
      this.snackService.printErrorCustom('Usuario no autenticado');
      this.router.navigate(['/login']).then(() => window.location.reload());
    }

    const token = this.tokenService.getJwtToken();
    if (token && !this.tokenService.validateTokenExpiration(token)) {
      // Token válido, agrega el token al header Authorization
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`,
        },
      });
    } else {
      // Token inválido o expirado, redirige al usuario a la página de inicio de sesión
      this.tokenService.resetLocalStorage();
      this.router.navigate(['/login']).then(() => window.location.reload());
    }

    return next.handle(request);
  }
}
