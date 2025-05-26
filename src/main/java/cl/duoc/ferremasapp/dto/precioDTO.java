package cl.duoc.ferremasapp.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PrecioDTO {
    private LocalDateTime fecha;
    private BigDecimal valor;
}