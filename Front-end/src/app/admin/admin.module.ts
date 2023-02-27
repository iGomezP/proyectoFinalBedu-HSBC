import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminPanelComponent } from './admin-panel/admin-panel.component';
import { AdminGuard } from '../guards/admin.guard';
import { AdminFunkosComponent } from './admin-panel/admin-funkos/admin-funkos.component';
import { MatTabsModule } from '@angular/material/tabs';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { HttpClientModule } from '@angular/common/http';
import { MatCardModule } from '@angular/material/card';

@NgModule({
  declarations: [AdminPanelComponent, AdminFunkosComponent],
  imports: [
    CommonModule,
    MatTabsModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatCardModule,
    HttpClientModule,
    ReactiveFormsModule,
  ],
  providers: [AdminGuard],
})
export class AdminModule {}
