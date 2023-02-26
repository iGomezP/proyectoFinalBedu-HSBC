import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/api/auth.service';
import { JwtService } from 'src/app/services/api/jwt.service';
import { SnackbarService } from 'src/app/services/snackbar.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  form = {
    username: 'admin@admin.com',
    password: 'Admin@123',
  };
  private fullRes: any;
  hidePassword = true;

  constructor(
    private authService: AuthService,
    private tokenService: JwtService,
    private router: Router,
    private snackService: SnackbarService
  ) {}

  login(): void {
    const { username, password } = this.form;
    console.log('Cargando...');
    this.authService.apiLogin(username, password).subscribe({
      next: (res) => (this.fullRes = res),
      error: (error) => this.snackService.printErrorLogin(error),
      complete: () => {
        // Enviar usuario y token a un servicio
        this.saveToken();
        this.router
          .navigate(['/productos'])
          .then(() => window.location.reload());
      },
    });
  }

  saveToken() {
    this.resetForm();
    this.tokenService.saveFullToken(this.fullRes);
  }

  resetForm() {
    this.form.username = '';
    this.form.password = '';
  }

  ngOnInit(): void {
    if (this.tokenService.getJwtToken()) {
      this.router.navigate(['/productos']);
    }
  }
}
