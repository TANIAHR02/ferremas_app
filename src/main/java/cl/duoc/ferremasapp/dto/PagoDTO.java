package cl.duoc.ferremasapp.dto;

import lombok.Data;

@Data
public class PagoDTO {
    private Integer pedidoId;
    private double monto;
    private String metodoPago; // DEBITO, CREDITO, TRANSFERENCIA
}
