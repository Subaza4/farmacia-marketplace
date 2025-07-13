package com.example.marketplace.services.impl;

import com.example.marketplace.model.Categoria;
import com.example.marketplace.repository.CategoriaRepository;
import com.example.marketplace.services.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;  // Corregido: era categoriaRepositor

    @Override
    public List<Categoria> listarTodas() {
        return categoriaRepository.findAll();
    }

    @Override
    public Optional<Categoria> obtenerPorId(Long id) {
        return categoriaRepository.findById(id);
    }

    @Override
    public Categoria guardar(Categoria categoria) {
        categoria.setId(null);
        return categoriaRepository.save(categoria);
    }

    @Override
    public Categoria actualizar(Long id, Categoria categoriaActualizada) {
        return categoriaRepository.findById(id)
                .map(categoria -> {
                    categoria.setNombre(categoriaActualizada.getNombre());
                    categoria.setDescripcion(categoriaActualizada.getDescripcion());
                    return categoriaRepository.save(categoria);
                })
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con id: " + id));
    }

    @Override
    public void eliminar(Long id) {
        categoriaRepository.deleteById(id);
    }
}