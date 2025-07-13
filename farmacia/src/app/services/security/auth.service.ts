import {HttpClient, HttpEvent} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {LoginRequest} from '../../model/request/login-request.model';
import {environment} from '../../../environments/environment';
import {LoginResponse} from "../../model/response/login-response.model";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = environment.loginEndpoint;
  private apiRolUrl = environment.getRolEndpoint;

  constructor(private http: HttpClient) {}

  login(data: LoginRequest): Observable<string> {
    return this.http.post(this.apiUrl, data, { responseType: 'text' });
  }

  saveSession(token: string, correo:string): void {
    localStorage.setItem('token', token);
  }

  saveToken(token: string): void {
    localStorage.setItem('token', token);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  getRol(): string | null{
    return localStorage.getItem('rol');
  }

  logout(): void {
    localStorage.removeItem('token');
  }

  getUserRol(correo: LoginRequest): Observable<string> {
    const params = { correo: correo.correo };
    return this.http.get<string>(this.apiRolUrl, { params });
  }

}
