package com.example.marketplace.services.impl;

import com.example.marketplace.model.*;
import com.example.marketplace.model.utils.ItemCarrito;
import com.example.marketplace.repository.*;
import com.example.marketplace.services.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class CarritoServiceImpl implements CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ItemCarritoRepository itemCarritoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    @Override
    public Carrito obtenerCarritoPorUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return carritoRepository.findByUsuario(usuario)
                .orElseGet(() -> {
                    Carrito nuevo = Carrito.builder()
                            .usuario(usuario)
                            .items(new ArrayList<>())
                            .total(BigDecimal.ZERO)
                            .build();
                    return carritoRepository.save(nuevo);
                });
    }

    @Override
    public Carrito agregarProducto(Long usuarioId, Long productoId, int cantidad) {
        Carrito carrito = obtenerCarritoPorUsuario(usuarioId);
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        ItemCarrito item = itemCarritoRepository.findByCarritoAndProducto(carrito, producto)
                .orElse(null);

        if (item == null) {
            item = ItemCarrito.builder()
                    .carrito(carrito)
                    .producto(producto)
                    .cantidad(cantidad)
                    .precioUnitario(producto.getPrecio())
                    .subtotal(producto.getPrecio().multiply(BigDecimal.valueOf(cantidad)))
                    .build();
            carrito.getItems().add(item);
        } else {
            item.setCantidad(item.getCantidad() + cantidad);
            item.setSubtotal(item.getPrecioUnitario().multiply(BigDecimal.valueOf(item.getCantidad())));
        }

        calcularTotal(carrito);
        return carritoRepository.save(carrito);
    }

    @Override
    public Carrito actualizarCantidad(Long usuarioId, Long productoId, int cantidad) {
        Carrito carrito = obtenerCarritoPorUsuario(usuarioId);
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        ItemCarrito item = itemCarritoRepository.findByCarritoAndProducto(carrito, producto)
                .orElseThrow(() -> new RuntimeException("Producto no está en el carrito"));

        item.setCantidad(cantidad);
        item.setSubtotal(item.getPrecioUnitario().multiply(BigDecimal.valueOf(cantidad)));

        calcularTotal(carrito);
        return carritoRepository.save(carrito);
    }

    @Override
    public void eliminarProducto(Long usuarioId, Long productoId) {
        Carrito carrito = obtenerCarritoPorUsuario(usuarioId);
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        ItemCarrito item = itemCarritoRepository.findByCarritoAndProducto(carrito, producto)
                .orElseThrow(() -> new RuntimeException("Producto no está en el carrito"));

        carrito.getItems().remove(item);
        itemCarritoRepository.delete(item);

        calcularTotal(carrito);
        carritoRepository.save(carrito);
    }

    @Override
    public void vaciarCarrito(Long usuarioId) {
        Carrito carrito = obtenerCarritoPorUsuario(usuarioId);
        itemCarritoRepository.deleteAll(carrito.getItems());
        carrito.getItems().clear();
        carrito.setTotal(BigDecimal.ZERO);
        carritoRepository.save(carrito);
    }

    private void calcularTotal(Carrito carrito) {
        BigDecimal total = carrito.getItems().stream()
                .map(ItemCarrito::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        carrito.setTotal(total);
    }

    @Override
    public Pedido realizarCheckout(Long usuarioId) {
        Carrito carrito = obtenerCarritoPorUsuario(usuarioId);

        if (carrito.getItems().isEmpty()) {
            throw new RuntimeException("El carrito está vacío");
        }

        Pedido pedido = Pedido.builder()
                .usuario(carrito.getUsuario())
                .fechaPedido(LocalDateTime.now())
                .total(carrito.getTotal())
                .build();

        pedido = pedidoRepository.save(pedido);

        for (ItemCarrito item : carrito.getItems()) {
            DetallePedido detalle = DetallePedido.builder()
                    .pedido(pedido)
                    .producto(item.getProducto())
                    .cantidad(item.getCantidad())
                    .precioUnitario(item.getPrecioUnitario())
                    .subtotal(item.getSubtotal())
                    .build();
            detallePedidoRepository.save(detalle);
        }

        vaciarCarrito(usuarioId); // Reutiliza método existente

        return pedido;
    }
}