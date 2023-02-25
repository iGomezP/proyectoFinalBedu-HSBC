import { Injectable } from '@angular/core';
import jwtDecode from 'jwt-decode';

@Injectable({
  providedIn: 'root',
})
export class JwtTokenService {
  private jwtToken: string = '';
  private jwtUser: string = '';
  private decodedToken: any;

  constructor() {}

  processFullToken(token: string, user: string) {
    // Verificar que existen y asignarlos a variables
    this.setTokenAndUser(token, user);

    // Decodificar el token
    this.setDecodedToken(this.jwtToken);
    console.log(this.decodedToken);

    //console.log(this.isTokenExpired());
  }

  setTokenAndUser(token: string, user: string) {
    if (token && user) {
      this.jwtToken = token;
      this.jwtUser = user;
    }
  }

  setDecodedToken(token: string) {
    this.decodedToken = jwtDecode(token);
  }

  // getUser() {
  //   this.decodeToken();
  //   return this.decodedToken ? this.decodedToken.name : null;
  // }

  // getEmailId() {
  //   this.decodeToken();
  //   return this.decodedToken ? this.decodedToken.email : null;
  // }

  isTokenExpired(): boolean {
    const { exp }: any = this.decodedToken;
    if (exp) {
      return 1000 * exp - new Date().getTime() < 5000;
    } else {
      return false;
    }
  }
}
