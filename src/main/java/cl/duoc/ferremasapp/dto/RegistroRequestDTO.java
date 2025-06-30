package cl.duoc.ferremasapp.dto;

import lombok.Data;

@Data
public class RegistroRequestDTO {
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private String telefono;
    private String direccion;
} 