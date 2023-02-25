import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminPanelComponent } from './admin/admin-panel/admin-panel.component';
import { AdminProductosComponent } from './admin/admin-productos/admin-productos.component';
import { ProductosComponent } from './dashboard/components/productos/productos.component';
import { UsuariosComponent } from './dashboard/components/usuarios/usuarios.component';
import { AdminGuard } from './guards/admin.guard';
import { BienvenidaComponent } from './home/components/bienvenida/bienvenida.component';
import { LoginComponent } from './login/components/login/login.component';
import { ForbiddenComponent } from './shared/components/forbidden/forbidden.component';
import { NotFoundComponent } from './shared/components/not-found/not-found.component';

const routes: Routes = [
  {
    path: 'admin',
    component: AdminPanelComponent,
    canActivate: [AdminGuard],
    children: [
      {
        path: '**',
        component: AdminProductosComponent,
      },
    ],
    canActivateChild: [AdminGuard],
  },
  { path: 'usuarios', component: UsuariosComponent },
  { path: 'productos', component: ProductosComponent },
  { path: 'login', component: LoginComponent },
  { path: 'forbidden', component: ForbiddenComponent },
  { path: '', component: BienvenidaComponent, pathMatch: 'full' },
  { path: '**', component: NotFoundComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
