import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './components/login/login.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { HttpClientModule } from '@angular/common/http';
import { MatCardModule } from '@angular/material/card';
import { SignupComponent } from './components/signup/signup.component';
import { SignupDireccionComponent } from './components/signup/signup-direccion/signup-direccion.component';
import { MatGridList, MatGridListModule } from '@angular/material/grid-list';

@NgModule({
  declarations: [LoginComponent, SignupComponent, SignupDireccionComponent],
  imports: [
    CommonModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatCardModule,
    MatGridListModule,
    HttpClientModule,
    ReactiveFormsModule,
  ],
})
export class LoginModule {}
