// src/app/auth/no-auth.guard.ts
import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class NoAuthGuard implements CanActivate {
  constructor(private router: Router) {}

  canActivate(): boolean {
    const token = localStorage.getItem('token');
    if (token) {
      // Ya está autenticado, lo mandamos a mercado
      this.router.navigate(['/mercado']);
      return false;
    }
    return true; // Si no tiene token, puede ir al login
  }
}
