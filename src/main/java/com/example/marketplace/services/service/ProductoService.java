package com.example.marketplace.services.service;

import com.example.marketplace.model.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoService {

    List<Producto> listarTodos();

    Optional<Producto> obtenerPorId(Long id);

    Producto guardar(Producto producto);

    Producto actualizar(Long id, Producto productoActualizado);

    void eliminar(Long id);

    List<Producto> buscarPorNombre(String nombre);

    List<Producto> listarPorCategoria(Long categoriaId);
}