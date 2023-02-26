import { Injectable } from '@angular/core';
import jwt_decode from 'jwt-decode';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})

// Renombrar a JwtService si el otro ya no se usa
export class JwtService {
  private userJwtToken: any;
  private userName: any;
  //private userRole: any;

  // Guardar jwt completo, usuario y rol
  saveFullToken(fullToken: any) {
    if (fullToken) {
      this.resetLocalStorage();
      this.userJwtToken = fullToken.headers.get('Authorization');
      this.userName = fullToken.body.User;
      localStorage.setItem(
        environment.JWT_TOKEN,
        JSON.stringify(this.userJwtToken)
      );
      localStorage.setItem(environment.JWT_USER, JSON.stringify(this.userName));
      this.setUserRole(this.userJwtToken);
    }
  }

  // Quitar Bearer y devolver
  getJwtToken(): string {
    let fullToken: string = '';
    try {
      fullToken = this.getFullToken(environment.JWT_TOKEN);
    } catch (error) {}
    let cleanToken: string = '';
    if (fullToken) {
      cleanToken = this.cleanFullToken(fullToken);
    }
    return cleanToken;
  }

  resetLocalStorage() {
    localStorage.clear();
  }

  private cleanFullToken(fullToken: string): string {
    return fullToken.replace('Bearer ', '');
  }

  private getFullToken(key: string) {
    return JSON.parse(localStorage.getItem(key) || '');
  }

  // Recibir token limpio y devuelve false si no ha expirado

  validateTokenExpiration(token: string): boolean {
    let exp: any;
    let isJwtExpired = false;
    try {
      exp = jwt_decode(token);
    } catch (e) {
      if (!exp) isJwtExpired = true;
    }
    const currentTime = new Date().getTime() / 1000;

    if (currentTime > exp) isJwtExpired = true;

    return isJwtExpired;
  }

  // Obtener el usuario
  getUserName(): string {
    let userName = '';
    try {
      userName = JSON.parse(localStorage.getItem(environment.JWT_USER) || '');
    } catch (Error) {
      return '';
    } finally {
      return userName;
    }
  }

  // Guardar el rol u obtenerlo

  private setUserRole(fullToken: string) {
    const cleanToken = this.cleanFullToken(fullToken);
    const userRole = this.getJwtRole(cleanToken);
    localStorage.setItem(environment.JWT_ROLE, JSON.stringify(userRole));
  }

  getUserRole(): string {
    let rol: string;
    try {
      rol = JSON.parse(localStorage.getItem(environment.JWT_ROLE) || '');
      return rol;
    } catch (error) {
      return '';
    }
  }

  // getUserRole(key: string): string {
  //   try {
  //     const token = this.getToken(key);
  //     const tempRole = this.getJwtRole(token);
  //     //console.log(tempRole);
  //     return tempRole;
  //   } catch (error) {}
  //   return '';
  // }

  private getJwtRole(fullToken: string): string {
    const decodedToken = jwt_decode(fullToken);
    const { authorities }: any = decodedToken;
    return authorities[0];
  }
}
