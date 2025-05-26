package cl.duoc.ferremasapp.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class PagoResponseDTO {
    private String token;
    private String url;
    private String estado;
    private String ordenCompra;
    private BigDecimal monto;
    private String mensajeError;
}