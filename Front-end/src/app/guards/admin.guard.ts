import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  CanActivateChild,
  Router,
  RouterStateSnapshot,
  UrlTree,
} from '@angular/router';
import { Observable } from 'rxjs';
import { JwtService } from '../services/api/jwt.service';

@Injectable({
  providedIn: 'root',
})
export class AdminGuard implements CanActivate, CanActivateChild {
  constructor(private tokenService: JwtService, private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ):
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree>
    | boolean
    | UrlTree {
    const rol = this.tokenService.getUserRole();
    if (rol === 'ADMIN') {
      return true;
    } else {
      this.router.navigate(['forbidden']);
      return false;
    }
  }

  canActivateChild(
    childRoute: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ):
    | boolean
    | UrlTree
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree> {
    const rol = this.tokenService.getUserRole();
    if (rol === 'ADMIN') {
      return true;
    } else {
      this.router.navigate(['forbidden']);
      return false;
    }
  }
}
