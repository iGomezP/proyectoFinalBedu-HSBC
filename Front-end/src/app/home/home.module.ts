import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BienvenidaComponent } from './components/bienvenida/bienvenida.component';
import { RouterLink } from '@angular/router';

@NgModule({
  declarations: [BienvenidaComponent],
  imports: [CommonModule, RouterLink],
})
export class HomeModule {}
