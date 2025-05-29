package cl.duoc.ferremasapp.dto;

import lombok.Data;

@Data
public class ProductoDTO {
    private Long id;
    private String codigo;
    private String nombre;
    private String descripcion;
    private Long categoriaId;
    private Long marcaId;
}
