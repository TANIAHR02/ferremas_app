package cl.duoc.ferremasapp.service;

import cl.duoc.ferremasapp.model.Mensaje;
import java.util.List;

public interface MensajeService {
    Mensaje enviarMensaje(Mensaje m);
    List<Mensaje> listarMensajesPorDestinatario(Long id);
    List<Mensaje> listarMensajesPorRemitente(Long id);
}
