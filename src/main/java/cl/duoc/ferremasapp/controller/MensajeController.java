package cl.duoc.ferremasapp.controller;

import cl.duoc.ferremasapp.dto.MensajeDTO;
import cl.duoc.ferremasapp.model.Mensaje;
import cl.duoc.ferremasapp.model.Usuario;
import cl.duoc.ferremasapp.service.MensajeService;
import cl.duoc.ferremasapp.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/mensajes")
@Tag(name = "Mensajes", description = "API para la gestión de mensajes entre clientes y vendedores")
public class MensajeController {
    
    private final MensajeService mensajeService;
    private final UsuarioService usuarioService;
    
    @Autowired
    public MensajeController(MensajeService mensajeService, UsuarioService usuarioService) {
        this.mensajeService = mensajeService;
        this.usuarioService = usuarioService;
    }
    
    @PostMapping
    @Operation(summary = "Enviar un nuevo mensaje")
    public ResponseEntity<MensajeDTO> enviarMensaje(@RequestBody MensajeDTO mensajeDTO) {
        try {
            Usuario remitente = usuarioService.findById(mensajeDTO.getRemitenteId())
                    .orElseThrow(() -> new NoSuchElementException("Remitente no encontrado"));
            
            Usuario destinatario = usuarioService.findById(mensajeDTO.getDestinatarioId())
                    .orElseThrow(() -> new NoSuchElementException("Destinatario no encontrado"));
            
            Mensaje mensaje = new Mensaje();
            mensaje.setRemitente(remitente);
            mensaje.setDestinatario(destinatario);
            mensaje.setAsunto(mensajeDTO.getAsunto());
            mensaje.setContenido(mensajeDTO.getContenido());
            mensaje.setLeido(false);
            
            Mensaje mensajeGuardado = mensajeService.save(mensaje);
            return ResponseEntity.status(HttpStatus.CREATED).body(convertirAMensajeDTO(mensajeGuardado));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/recibidos/{usuarioId}")
    @Operation(summary = "Obtener mensajes recibidos por un usuario")
    public ResponseEntity<List<MensajeDTO>> getMensajesRecibidos(@PathVariable Integer usuarioId) {
        List<Mensaje> mensajes = mensajeService.findByDestinatarioId(usuarioId);
        List<MensajeDTO> mensajesDTO = mensajes.stream()
                .map(this::convertirAMensajeDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(mensajesDTO);
    }
    
    @GetMapping("/enviados/{usuarioId}")
    @Operation(summary = "Obtener mensajes enviados por un usuario")
    public ResponseEntity<List<MensajeDTO>> getMensajesEnviados(@PathVariable Integer usuarioId) {
        List<Mensaje> mensajes = mensajeService.findByRemitenteId(usuarioId);
        List<MensajeDTO> mensajesDTO = mensajes.stream()
                .map(this::convertirAMensajeDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(mensajesDTO);
    }
    
    @PutMapping("/{id}/leer")
    @Operation(summary = "Marcar un mensaje como leído")
    public ResponseEntity<MensajeDTO> marcarComoLeido(@PathVariable Integer id) {
        try {
            Mensaje mensaje = mensajeService.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Mensaje no encontrado"));
            
            mensaje.setLeido(true);
            Mensaje mensajeActualizado = mensajeService.save(mensaje);
            
            return ResponseEntity.ok(convertirAMensajeDTO(mensajeActualizado));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Método auxiliar para convertir Mensaje a MensajeDTO
    private MensajeDTO convertirAMensajeDTO(Mensaje mensaje) {
        MensajeDTO dto = new MensajeDTO();
        dto.setId(mensaje.getId());
        dto.setRemitenteId(mensaje.getRemitente().getId());
        dto.setRemitenteNombre(mensaje.getRemitente().getNombre() + " " + mensaje.getRemitente().getApellido());
        dto.setDestinatarioId(mensaje.getDestinatario().getId());
        dto.setDestinatarioNombre(mensaje.getDestinatario().getNombre() + " " + mensaje.getDestinatario().getApellido());
        dto.setAsunto(mensaje.getAsunto());
        dto.setContenido(mensaje.getContenido());
        dto.setFechaEnvio(mensaje.getFechaEnvio());
        dto.setLeido(mensaje.getLeido());
        return dto;
    }
}