package cl.duoc.ferremasapp.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ActualizarPrecioDTO {
    private BigDecimal nuevoPrecio;
    private String motivo;
    private String usuario;
} 