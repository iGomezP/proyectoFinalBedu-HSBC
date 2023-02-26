import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { JwtService } from './jwt.service';

const AUTH_API = 'https://189.234.176.58/APIGeros/api/';
//const AUTH_API = 'http://localhost:8080/';

@Injectable({
  providedIn: 'root',
})
export class FunkosService {
  constructor(
    private httpClient: HttpClient,
    private storageService: JwtService
  ) {}

  getAllFunkosService(): Observable<any> {
    const token = this.storageService.getJwtToken();
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      }),
    };
    return this.httpClient.get(AUTH_API + 'productos/funkos', httpOptions);
  }
}
