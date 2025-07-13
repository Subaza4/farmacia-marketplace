import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class ProductosService {
  //private apiUrl = 'http://localhost:8080/api/productos';
  private apiUrl = environment.productosEndPoint;

  constructor(private http: HttpClient) {}

  getProductos(): Observable<any[]> {
    const token = localStorage.getItem('token');
    const headers = {
      'Authorization': `Bearer ${token}`
    };
    return this.http.get<any[]>(this.apiUrl, { headers });
  }
}
