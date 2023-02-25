import { Direccion } from './direccion.model';

export interface UsuarioModel {
  name: string;
  email: string;
  userPassword: string;
  direccion: Direccion;
}
