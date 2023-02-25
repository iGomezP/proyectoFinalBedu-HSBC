import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FooterComponent } from './components/footer/footer.component';
import { HeaderComponent } from './components/header/header.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { RouterLink } from '@angular/router';
import { ForbiddenComponent } from './components/forbidden/forbidden.component';
import { AdminGuard } from '../guards/admin.guard';

@NgModule({
  declarations: [
    HeaderComponent,
    FooterComponent,
    NotFoundComponent,
    ForbiddenComponent,
  ],
  imports: [CommonModule, MatIconModule, MatSnackBarModule, RouterLink],
  exports: [HeaderComponent, FooterComponent, NotFoundComponent],
  providers: [AdminGuard],
})
export class SharedModule {}
