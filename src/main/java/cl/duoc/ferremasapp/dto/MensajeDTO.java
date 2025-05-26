package cl.duoc.ferremasapp.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MensajeDTO {
    private Integer id;
    private Integer remitenteId;
    private String remitenteNombre;
    private Integer destinatarioId;
    private String destinatarioNombre;
    private String asunto;
    private String contenido;
    private LocalDateTime fechaEnvio;
    private Boolean leido;
}