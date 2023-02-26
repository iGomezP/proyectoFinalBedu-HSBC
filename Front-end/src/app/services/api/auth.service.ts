import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UsuarioModel } from 'src/app/models/usuario.model';
import { environment } from 'src/environments/environment.dev';

const AUTH_API = environment.API_URL;

const httpOptions = new HttpHeaders().set('Content-Type', 'application/json');

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private httpClient: HttpClient) {}

  apiLogin(username: string, password: string): Observable<any> {
    console.log(httpOptions);
    return this.httpClient.post<any>(
      AUTH_API + 'auth/login',
      {
        username: username,
        password: password,
      },
      {
        headers: httpOptions,
        observe: 'response',
      }
    );
  }

  apiRegister(usuario: UsuarioModel): Observable<any> {
    return this.httpClient.post<any>(AUTH_API + 'auth/register', usuario, {
      headers: httpOptions,
    });
  }
}
