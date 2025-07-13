import {Component, OnInit} from '@angular/core';
import {FormControl} from '@angular/forms';
import {debounceTime} from 'rxjs';
import {ProductosService} from '../../services/producto/productos.service';
import {Producto} from "../../model/producto.model";

@Component({
  selector: 'app-mercado',
  templateUrl: './mercado.component.html',
  styleUrls: ['./mercado.component.scss'],
  standalone: false
})
export class MercadoComponent implements OnInit{
  filtro = new FormControl('');
  productos: Producto[] = [];
  productosFiltrados: Producto[] = [];


  constructor(private productosService: ProductosService) {}

  ngOnInit(): void {
    this.productosService.getProductos().subscribe((data: Producto[]) => {
      this.productos = data;
      this.productosFiltrados = data;
    });

    this.filtro.valueChanges.pipe(debounceTime(300)).subscribe((valor) => {
      this.productosFiltrados = this.productos.filter(producto =>
        producto.nombre.toLowerCase().includes(valor ? valor.toLowerCase() : "") ||
        producto.categoria.toLowerCase().includes(valor ? valor.toLowerCase() : "")
      );
    });
  }

}
