package cl.duoc.ferremasapp.dto;

import lombok.Data;

@Data
public class DetallePedidoDTO {
    private Integer productoId;
    private int cantidad;
    private double precioUnitario;
}
