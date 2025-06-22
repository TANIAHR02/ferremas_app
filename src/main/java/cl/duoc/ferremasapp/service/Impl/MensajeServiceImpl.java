package cl.duoc.ferremasapp.service.Impl;

import cl.duoc.ferremasapp.model.Mensaje;
import cl.duoc.ferremasapp.repository.MensajeRepository;
import cl.duoc.ferremasapp.service.MensajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MensajeServiceImpl implements MensajeService {

    @Autowired
    private MensajeRepository mensajeRepository;

    @Override
    public Mensaje enviarMensaje(Mensaje mensaje) {
        return mensajeRepository.save(mensaje);
    }

    @Override
    public List<Mensaje> listarMensajesPorDestinatario(Long destinatarioId) {
        
        return mensajeRepository.findAll();
    }

    @Override
    public List<Mensaje> listarMensajesPorRemitente(Long remitenteId) {
        
        return mensajeRepository.findAll();
    }
}
