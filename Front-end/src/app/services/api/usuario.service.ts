import { Injectable } from '@angular/core';
import { UsuarioModel } from 'src/app/models/usuario.model';

@Injectable({
  providedIn: 'root',
})
export class UsuarioService {
  private usuario!: UsuarioModel;

  constructor() {
    this.usuario = {
      name: '',
      email: '',
      password: '',
      direccion: {
        calle: '',
        numero: 0,
        ciudad: '',
        estado: '',
        codigoPostal: 0,
        colonia: '',
      },
    };
  }

  getUsuario() {
    return this.usuario;
  }

  setUsuario(usuario: UsuarioModel) {
    this.usuario = usuario;
  }
}
