import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminPanelComponent } from './admin-panel/admin-panel.component';
//import { AdminGuard } from '../guards/admin.guard';
import { AdminProductosComponent } from './admin-productos/admin-productos.component';

@NgModule({
  declarations: [AdminPanelComponent, AdminProductosComponent],
  imports: [CommonModule],
  //providers: [AdminGuard],
})
export class AdminModule {}
