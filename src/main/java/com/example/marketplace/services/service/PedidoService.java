package com.example.marketplace.services.service;

import com.example.marketplace.model.Pedido;

import java.util.List;
import java.util.Optional;

public interface PedidoService {
    List<Pedido> listarTodos();
    Optional<Pedido> obtenerPorId(Long id);
    Pedido guardar(Pedido pedido);
    Pedido actualizarEstado(Long id, String nuevoEstado);
    void eliminar(Long id);
}