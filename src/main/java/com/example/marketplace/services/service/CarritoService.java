package com.example.marketplace.services.service;

import com.example.marketplace.model.Carrito;
import com.example.marketplace.model.Pedido;

public interface CarritoService {

    Carrito obtenerCarritoPorUsuario(Long usuarioId);

    Carrito agregarProducto(Long usuarioId, Long productoId, int cantidad);

    Carrito actualizarCantidad(Long usuarioId, Long productoId, int cantidad);

    void eliminarProducto(Long usuarioId, Long productoId);

    void vaciarCarrito(Long usuarioId);

    Pedido realizarCheckout(Long usuarioId);
}