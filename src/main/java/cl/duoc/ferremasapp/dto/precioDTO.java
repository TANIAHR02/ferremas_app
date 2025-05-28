package cl.duoc.ferremasapp.dto;

import java.math.BigDecimal;
import cl.duoc.ferremasapp.DTO.precioDTO;


public class precioDTO {
    private Long id;
    private BigDecimal precio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }
}
