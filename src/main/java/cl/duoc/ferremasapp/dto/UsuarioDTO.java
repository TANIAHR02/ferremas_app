package cl.duoc.ferremasapp.dto;

import lombok.Data;

@Data
public class UsuarioDTO {
    private Integer id;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String direccion;
    private String rol;
}
