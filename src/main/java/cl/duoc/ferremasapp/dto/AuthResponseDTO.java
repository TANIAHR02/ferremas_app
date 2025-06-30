package cl.duoc.ferremasapp.dto;

import cl.duoc.ferremasapp.model.Usuario;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AuthResponseDTO {
    private Boolean success;
    private String message;
    private String token;
    private Usuario usuario;
    private LocalDateTime timestamp;
} 