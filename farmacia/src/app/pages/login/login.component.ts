import { Component } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../../services/security/auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  standalone: false
})
export class LoginComponent {
  loginForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      correo: ['', [Validators.required, Validators.email]],
      contrasena: ['', [Validators.required, Validators.minLength(3)]],
    });
  }

  onSubmit(): void {
    if (this.loginForm.invalid) return;

    const { correo, contrasena } = this.loginForm.value;

    this.authService.login({ correo, contrasena }).subscribe({
      next: (token) => {
        console.log("token", token)
        // Guarda el token
        this.authService.saveToken(token);

        // Guarda el correo en localStorage si lo necesitas después
        localStorage.setItem('correo', correo);

        // Redirecciona
        this.router.navigate(['/productos']);
      },
      error: (err) => {
        console.error('Error al iniciar sesión:', err);
      }
    });
  }
}
