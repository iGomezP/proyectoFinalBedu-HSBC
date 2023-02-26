import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { JwtService } from './jwt.service';
import { environment } from 'src/environments/environment.dev';

const AUTH_API = environment.API_URL;

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
