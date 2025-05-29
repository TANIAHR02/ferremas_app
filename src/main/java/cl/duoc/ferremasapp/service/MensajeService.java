package cl.duoc.ferremasapp.service;

import java.util.List;

import cl.duoc.ferremasapp.model.Mensaje;

public interface MensajeService {
    Mensaje enviarMensaje(Mensaje mensaje);
    List<Mensaje> listarMensajesPorDestinatario(Long destinatarioId);
    List<Mensaje> listarMensajesPorRemitente(Long remitenteId);
}
