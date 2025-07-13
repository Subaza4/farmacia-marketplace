import {Component, OnInit} from '@angular/core';
import {Router, RouterOutlet} from '@angular/router';
import {MatSidenavContainer, MatSidenavModule} from '@angular/material/sidenav';
import {MatToolbar} from '@angular/material/toolbar';
import {MatNavList} from '@angular/material/list';
import {MatIcon} from '@angular/material/icon';
import {AuthService} from '../services/security/auth.service';
import {Observable} from 'rxjs';
import {LoginRequest} from '../model/request/login-request.model';

@Component({
  selector: 'app-layout',
  templateUrl: './layout.component.html',
  imports: [
    MatSidenavContainer,
    MatToolbar,
    MatNavList,
    RouterOutlet,
    MatIcon,
    MatSidenavModule
  ],
  styleUrls: ['./layout.component.scss']
})
export class LayoutComponent implements OnInit{

  constructor(
    private router: Router,
    private authService: AuthService
  ) {}

  public ngOnInit() {
    const correo = localStorage.getItem('correo');

    if (correo) {
      const req: LoginRequest = {
        correo: correo,
        contrasena: '' // Puedes ajustar esto si lo necesitas
      };

      this.authService.getUserRol(req).subscribe({
        next: (rol: string) => {
          this.esAdmin = rol === 'ADMIN';
        },
        error: (err) => {
          console.error('Error obteniendo rol:', err);
        }
      });
    } else {
      console.warn('No se encontró el correo en localStorage');
    }
  }

  public esAdmin: boolean = false;

  logout(): void {
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
  }
}
