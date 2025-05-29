package cl.duoc.ferremasapp.dto;

import lombok.Data;

@Data
public class PagoResponseDTO {
    private String transaccionId;
    private String estado;
    private String mensaje;
}
