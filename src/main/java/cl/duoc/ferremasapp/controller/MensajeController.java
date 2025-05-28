package cl.duoc.ferremasapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.ferremasapp.model.Mensaje;
import cl.duoc.ferremasapp.service.MensajeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/mensajes")
@Tag(name = "Mensajes", description = "API para la gestión de mensajes")
public class MensajeController {

    private final MensajeService mensajeService;

    @Autowired
    public MensajeController(MensajeService mensajeService) {
        this.mensajeService = mensajeService;
    }

    @PostMapping
    @Operation(summary = "Enviar un mensaje")
    public ResponseEntity<Mensaje> enviar(@RequestBody Mensaje mensaje) {
        return ResponseEntity.ok(mensajeService.enviarMensaje(mensaje));
    }

    @GetMapping("/destinatario/{id}")
    @Operation(summary = "Listar mensajes por destinatario")
    public ResponseEntity<List<Mensaje>> listarPorDestinatario(@PathVariable Long id) {
        return ResponseEntity.ok(mensajeService.listarMensajesPorDestinatario(id));
    }

    @GetMapping("/remitente/{id}")
    @Operation(summary = "Listar mensajes por remitente")
    public ResponseEntity<List<Mensaje>> listarPorRemitente(@PathVariable Long id) {
        return ResponseEntity.ok(mensajeService.listarMensajesPorRemitente(id));
    }
}
