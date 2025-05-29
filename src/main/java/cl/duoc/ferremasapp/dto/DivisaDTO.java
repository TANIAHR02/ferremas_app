package cl.duoc.ferremasapp.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DivisaDTO {
    private String codigo;
    private BigDecimal tasaCambio;
}
