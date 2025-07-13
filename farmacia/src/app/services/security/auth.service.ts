import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {LoginRequest} from '../../model/request/login-request.model';
import {environment} from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = environment.loginEndpoint;

  constructor(private http: HttpClient) {}

  login(data: LoginRequest): Observable<string> {
    console.log("login")
    return this.http.post(this.apiUrl, data, { responseType: 'text' });
  }

  saveToken(token: string): void {
    localStorage.setItem('token', token);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  logout(): void {
    localStorage.removeItem('token');
  }
}
