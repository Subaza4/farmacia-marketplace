package com.example.marketplace.model;

import com.example.marketplace.model.utils.EstadoPedido;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Cliente que hace el pedido
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Fecha y hora del pedido
    @Column(name = "fecha_pedido", nullable = false)
    private LocalDateTime fechaPedido;

    // Estado del pedido (PENDIENTE, ENVIADO, ENTREGADO, CANCELADO)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPedido estado;

    // Total del pedido
    @Column(nullable = false)
    private BigDecimal total;

    // Lista de productos en el pedido (relación con DetallePedido)
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallePedido> detalles;
}