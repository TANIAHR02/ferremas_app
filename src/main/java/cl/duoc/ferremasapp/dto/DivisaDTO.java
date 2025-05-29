package cl.duoc.ferremasapp.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class DivisaDTO {
    private String codigo;
    private BigDecimal tasaCambio;

    public DivisaDTO(String codigo, BigDecimal tasaCambio) {
        this.codigo = codigo;
        this.tasaCambio = tasaCambio;
    }
}