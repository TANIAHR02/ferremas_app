package cl.duoc.ferremasapp.dto;

import lombok.Data;

@Data
public class PagoDTO {
    private Long pedidoId;
    private double monto;
    private String metodoPago; // DEBITO, CREDITO, TRANSFERENCIA
}
