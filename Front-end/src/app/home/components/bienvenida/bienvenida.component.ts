import { Component, OnInit } from '@angular/core';
import { JwtService } from 'src/app/services/api/jwt.service';
import { SnackbarService } from 'src/app/services/snackbar.service';

@Component({
  selector: 'app-bienvenida',
  templateUrl: './bienvenida.component.html',
  styleUrls: ['./bienvenida.component.scss'],
})
export class BienvenidaComponent {
  constructor(
    private localStorage: JwtService,
    private snackService: SnackbarService
  ) {}

  // ngOnInit(): void {
  //   const isLogged = this.localStorage.validateTokenExpiration({
  //     token: 'User',
  //   });
  //   if (!isLogged) {
  //     this.snackService.printBienvenida('¡Bienvenido!');
  //   }
  // }
}
