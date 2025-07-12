package com.example.marketplace.controller;

import com.example.marketplace.model.Carrito;
import com.example.marketplace.model.Pedido;
import com.example.marketplace.services.service.CarritoService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carrito")
@CrossOrigin(origins = "*")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @PreAuthorize("hasRole('CLIENTE')")
    @GetMapping("/{usuarioId}")
    public ResponseEntity<Carrito> obtenerCarrito(@PathVariable Long usuarioId) {
        Carrito carrito = carritoService.obtenerCarritoPorUsuario(usuarioId);
        return ResponseEntity.ok(carrito);
    }

    @PreAuthorize("hasRole('CLIENTE')")
    @PostMapping("/{usuarioId}/agregar")
    public ResponseEntity<Carrito> agregarProducto(
            @PathVariable Long usuarioId,
            @RequestBody CarritoProductoRequest request
    ) {
        Carrito actualizado = carritoService.agregarProducto(usuarioId, request.getProductoId(), request.getCantidad());
        return ResponseEntity.ok(actualizado);
    }

    @PutMapping("/{usuarioId}/actualizar")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<Carrito> actualizarCantidad(
            @PathVariable Long usuarioId,
            @RequestBody CarritoProductoRequest request
    ) {
        Carrito actualizado = carritoService.actualizarCantidad(usuarioId, request.getProductoId(), request.getCantidad());
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{usuarioId}/producto/{productoId}")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<Void> eliminarProducto(
            @PathVariable Long usuarioId,
            @PathVariable Long productoId
    ) {
        carritoService.eliminarProducto(usuarioId, productoId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('CLIENTE')")
    @DeleteMapping("/{usuarioId}/vaciar")
    public ResponseEntity<Void> vaciarCarrito(@PathVariable Long usuarioId) {
        carritoService.vaciarCarrito(usuarioId);
        return ResponseEntity.noContent().build();
    }

    @Data
    public static class CarritoProductoRequest {
        private Long productoId;
        private int cantidad;
    }

    @PostMapping("/{usuarioId}/checkout")
    public ResponseEntity<Pedido> realizarCheckout(@PathVariable Long usuarioId) {
        Pedido pedido = carritoService.realizarCheckout(usuarioId);
        return ResponseEntity.ok(pedido);
    }
}