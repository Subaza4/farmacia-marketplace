package com.example.marketplace.controller;

import com.example.marketplace.model.Pedido;
import com.example.marketplace.services.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<List<Pedido>> listarTodos() {
        return ResponseEntity.ok(pedidoService.listarTodos());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<Pedido> obtenerPorId(@PathVariable Long id) {
        return pedidoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Pedido> crear(@RequestBody Pedido pedido) {
        return ResponseEntity.ok(pedidoService.guardar(pedido));
    }

    @PutMapping("/{id}/estado")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<Pedido> actualizarEstado(@PathVariable Long id, @RequestParam String estado) {
        try {
            return ResponseEntity.ok(pedidoService.actualizarEstado(id, estado));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        pedidoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}