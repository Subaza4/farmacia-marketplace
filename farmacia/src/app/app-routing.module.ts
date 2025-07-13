import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { MercadoComponent } from './pages/mercado/mercado.component';
import { AuthGuard } from './store/shared/auth.guard';
import {NoAuthGuard} from './store/shared/no-auth.guard';
import {LayoutComponent} from "./layout/layout.component";
import {UsuarioComponent} from "./pages/usuario/usuario.component";


const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent, canActivate: [NoAuthGuard] },
  {
    path: '',
    component: LayoutComponent,
    canActivate: [AuthGuard],
    children: [
      { path: 'mercado', component: MercadoComponent },
      { path: 'usuario', component: UsuarioComponent }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
