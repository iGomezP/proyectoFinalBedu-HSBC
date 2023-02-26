import { Component, OnInit } from '@angular/core';
import { NavigationExtras, Router } from '@angular/router';
import { AuthService } from 'src/app/services/api/auth.service';
import { JwtService } from 'src/app/services/api/jwt.service';
import { SnackbarService } from 'src/app/services/snackbar.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss'],
})
export class SignupComponent implements OnInit {
  form = {
    username: '',
    email: '',
    password: '',
  };

  isVerified = false;
  hidePassword = true;
  allConditionsMet = false;
  inputUserFocus: boolean = false;
  inputEmailFocus: boolean = false;
  inputPasswordFocus: boolean = false;
  inputUser: boolean = false;
  inputEmail: boolean = false;
  inputPassword: boolean = false;
  mostrarComponentePadre = true;
  mostrarComponenteHijo = false;

  constructor(
    private tokenService: JwtService,
    private router: Router,
    private snackService: SnackbarService
  ) {}

  signupDireccion() {
    this.verifyFields();
    this.verifyAllConditions();
    if (this.allConditionsMet) {
      this.mostrarComponentePadre = false;
      this.mostrarComponenteHijo = true;
    } else {
      this.verifyFields();
    }
  }

  ngOnInit(): void {
    if (this.tokenService.getJwtToken()) {
      this.router.navigate(['/productos']);
    }
  }

  verifyAllConditions() {
    if (this.inputUser && this.inputEmail && this.inputPassword) {
      this.allConditionsMet = true;
      console.log(this.allConditionsMet);
    } else {
      this.allConditionsMet = false;
      console.log(this.allConditionsMet);
    }
  }

  verifyFields() {
    if (this.form.username.length === 0) {
      this.snackService.printErrorCustom('El nombre no puede ir vacío');
      this.inputUser = false;
      return;
    }
    if (this.form.username.length < 6) {
      this.snackService.printErrorCustom(
        'El nombre no puede tener menos de 6 caracteres'
      );
      this.inputUser = false;
      return;
    }
    if (this.form.username.length > 30) {
      this.snackService.printErrorCustom(
        'El nombre no puede tener más de 30 caracteres'
      );
      this.inputUser = false;
      return;
    }
    if (this.form.email.length === 0) {
      this.snackService.printErrorCustom('El email no puede ir vacío');
      this.inputEmail = false;
      return;
    }
    if (this.form.password.length === 0) {
      this.snackService.printErrorCustom('El password no puede ir vacío');
      this.inputPassword = false;
      return;
    }
    if (this.form.password.length < 8) {
      this.snackService.printErrorCustom(
        'El password debe tener mínimo 8 caracteres'
      );
      this.inputPassword = false;
      return;
    }
    this.inputUser = true;
    this.inputEmail = true;
    this.inputPassword = true;
  }

  onUserFocus(): void {
    this.inputUserFocus = true;
  }

  onUserBlur(): void {
    this.inputUserFocus = false;
  }

  onEmailFocus(): void {
    this.inputEmailFocus = true;
  }

  onEmailBlur(): void {
    this.inputEmailFocus = false;
  }

  onPasswordFocus(): void {
    this.inputPasswordFocus = true;
  }

  onPasswordBlur(): void {
    this.inputPasswordFocus = false;
  }
}
