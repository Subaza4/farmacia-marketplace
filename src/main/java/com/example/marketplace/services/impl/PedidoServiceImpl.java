package com.example.marketplace.services.impl;
import com.example.marketplace.model.Pedido;
import com.example.marketplace.model.utils.EstadoPedido;
import com.example.marketplace.repository.PedidoRepository;
import com.example.marketplace.services.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Override
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    @Override
    public Optional<Pedido> obtenerPorId(Long id) {
        return pedidoRepository.findById(id);
    }

    @Override
    public Pedido guardar(Pedido pedido) {
        // Calcular total sumando subtotales de los detalles
        BigDecimal total = pedido.getDetalles().stream()
                .peek(detalle -> {
                    detalle.setPedido(pedido); // establecer relación inversa
                    detalle.setSubtotal(
                            detalle.getPrecioUnitario().multiply(BigDecimal.valueOf(detalle.getCantidad()))
                    );
                })
                .map(d -> d.getSubtotal())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        pedido.setTotal(total);
        pedido.setEstado(EstadoPedido.PENDIENTE);
        pedido.setId(null);

        return pedidoRepository.save(pedido);
    }

    @Override
    public Pedido actualizarEstado(Long id, String nuevoEstado) {
        return pedidoRepository.findById(id).map(pedido -> {
            pedido.setEstado(EstadoPedido.valueOf(nuevoEstado.toUpperCase()));
            return pedidoRepository.save(pedido);
        }).orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + id));
    }

    @Override
    public void eliminar(Long id) {
        pedidoRepository.deleteById(id);
    }
}