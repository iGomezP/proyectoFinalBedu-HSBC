import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UsuarioModel } from 'src/app/models/usuario.model';
import { AuthService } from 'src/app/services/api/auth.service';
import { SnackbarService } from 'src/app/services/snackbar.service';

@Component({
  selector: 'app-signup-direccion',
  templateUrl: './signup-direccion.component.html',
  styleUrls: ['./signup-direccion.component.scss'],
})
export class SignupDireccionComponent implements OnInit {
  @Input() inputData: any;
  usuario: UsuarioModel = {
    name: '',
    email: '',
    userPassword: '',
    direccion: {
      calle: '',
      numeroInterior: '',
      numeroExterior: '',
      ciudad: '',
      estado: '',
      codigoPostal: '',
      colonia: '',
      referencias: '',
    },
  };

  constructor(
    private router: Router,
    private authService: AuthService,
    private snackbarService: SnackbarService
  ) {}
  ngOnInit(): void {
    this.usuario.name = this.inputData.username;
    this.usuario.email = this.inputData.email;
    this.usuario.userPassword = this.inputData.password;
  }

  // signupBackwards() {
  //   console.log('enviando datos de regreso');
  // }

  signup() {
    //console.log('Haciendo post para el registro');
    this.authService.apiRegister(this.usuario).subscribe({
      next: (res) => {},
      error: (error) => {
        console.log(error.error.errores);
        this.snackbarService.printErrorCustom(
          'Hubo un error en el registro, revisa que los datos sean correctos'
        );
      },
      complete: () => {
        this.snackbarService.printBienvenida(
          'Registro exitoso, por favor inicia sesión'
        );
        this.router.navigate(['/login']);
      },
    });
  }
}
