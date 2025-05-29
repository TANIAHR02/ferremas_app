package cl.duoc.ferremasapp.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PrecioDTO {
    private BigDecimal precio;
    private String fechaInicio;
    private String fechaFin;
}
