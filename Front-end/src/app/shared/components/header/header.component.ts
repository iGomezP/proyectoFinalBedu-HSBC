import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { JwtService } from 'src/app/services/api/jwt.service';
import { SnackbarService } from 'src/app/services/snackbar.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent implements OnInit {
  isSessionExpired = false;
  userRoleLocal = '';
  roleVerified!: boolean;
  userName!: string;

  constructor(
    private tokenService: JwtService,
    private router: Router,
    private snackService: SnackbarService
  ) {}

  ngOnInit(): void {
    this.isSessionExpired = this.tokenService.validateTokenExpiration(
      this.tokenService.getJwtToken()
    );
    this.userRoleLocal = this.tokenService.getUserRole();
    if (this.isSessionExpired) {
      this.tokenService.resetLocalStorage();
      this.snackService.printErrorCustom(
        'La sesión ha expirado, inicia sesión nuevamente'
      );
    }
    if (this.userRoleLocal === 'ADMIN') {
      this.roleVerified = true;
    } else {
      this.roleVerified = false;
    }
    this.userName = this.tokenService.getUserName();
  }

  closeSession() {
    this.tokenService.resetLocalStorage();
    this.ngOnInit();
    this.snackService.printErrorCustom('Se ha cerrado la sesión exitosamente');
    this.router.navigate(['/login']).then(() => location.reload());
  }
}
