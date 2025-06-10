package cl.duoc.ferremasapp.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "pedidos")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private LocalDateTime fechaPedido;

    @Enumerated(EnumType.STRING)
    private EstadoPedido estado;

    @Enumerated(EnumType.STRING)
    private TipoEntrega tipoEntrega;

    private String direccionEntrega;

    @ManyToOne
    @JoinColumn(name = "sucursal_id")
    private Sucursal sucursal;

    private double total;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<DetallePedido> detalles;

    public enum EstadoPedido {
        PENDIENTE, APROBADO, RECHAZADO, EN_PREPARACION, DESPACHADO, ENTREGADO, CANCELADO
    }

    public enum TipoEntrega {
        RETIRO_EN_TIENDA, DESPACHO_A_DOMICILIO
    }
}
