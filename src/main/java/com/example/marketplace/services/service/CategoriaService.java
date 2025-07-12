package com.example.marketplace.services.service;

import com.example.marketplace.model.Categoria;

import java.util.List;
import java.util.Optional;

public interface CategoriaService {

    List<Categoria> listarTodas();

    Optional<Categoria> obtenerPorId(Long id);

    Categoria guardar(Categoria categoria);

    Categoria actualizar(Long id, Categoria categoriaActualizada);

    void eliminar(Long id);
}