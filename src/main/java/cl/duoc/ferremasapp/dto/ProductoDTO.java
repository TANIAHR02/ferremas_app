package cl.duoc.ferremasapp.dto;

import lombok.Data;

@Data
public class ProductoDTO {
    private String codigo;
    private String nombre;
    private String descripcion;
    private String categoria;
    private String marca;
    private double precio;
    private int stock;
}
