package cl.duoc.ferremasapp.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductoDTO {
    private String codigo;
    private String marca;
    private String nombre;
    private List<PrecioDTO> precios;
    private Integer stock;
}
