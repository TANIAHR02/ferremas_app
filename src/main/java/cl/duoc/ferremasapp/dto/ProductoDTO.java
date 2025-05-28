package cl.duoc.ferremasapp.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;
import cl.duoc.ferremasapp.DTO.productoDTO;

@Data
public class productoDTO {
    private String codigo;
    private String marca;
    private String nombre;
    private List<precioDTO> precios;
    private Integer stock;
}
