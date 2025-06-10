package cl.duoc.ferremasapp.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "pagos")
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    private BigDecimal monto;


    @Enumerated(EnumType.STRING)
    private MetodoPago metodoPago;

    @Enumerated(EnumType.STRING)
    private EstadoPago estado;

    private LocalDateTime fechaPago;

    private String transaccionId;

    public enum MetodoPago {
        DEBITO, CREDITO, TRANSFERENCIA
    }

    public enum EstadoPago {
        PENDIENTE, COMPLETADO, FALLIDO, CANCELADO
    }
}
