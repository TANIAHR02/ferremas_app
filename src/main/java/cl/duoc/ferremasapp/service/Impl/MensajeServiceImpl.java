package cl.duoc.ferremasapp.service.impl;

import cl.duoc.ferremasapp.model.Mensaje;
import cl.duoc.ferremasapp.repository.MensajeRepository;
import cl.duoc.ferremasapp.service.MensajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MensajeServiceImpl implements MensajeService {

    @Autowired
    private MensajeRepository mensajeRepository;

    @Override
    public Mensaje enviarMensaje(Mensaje m) {
        return mensajeRepository.save(m);
    }

    @Override
    public List<Mensaje> listarMensajesPorDestinatario(Long id) {
        return mensajeRepository.findAll().stream()
                .filter(m -> m.getDestinatario().getId().equals(id))
                .collect(Collectors.toList());
    }

    @Override
    public List<Mensaje> listarMensajesPorRemitente(Long id) {
        return mensajeRepository.findAll().stream()
                .filter(m -> m.getRemitente().getId().equals(id))
                .collect(Collectors.toList());
    }
}
