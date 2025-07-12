package com.example.marketplace.repository;

import com.example.marketplace.model.Carrito;
import com.example.marketplace.model.Producto;
import com.example.marketplace.model.utils.ItemCarrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface ItemCarritoRepository extends JpaRepository<ItemCarrito, Long> {
    List<ItemCarrito> findByCarrito(Carrito carrito);
    Optional<ItemCarrito> findByCarritoAndProducto(Carrito carrito, Producto producto);
}