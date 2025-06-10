package cl.duoc.ferremasapp.service.impl;

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
    public List<Mensaje> listarMensajes() {
        return mensajeRepository.findAll();
    }

    @Override
    public Mensaje guardarMensaje(Mensaje mensaje) {
        return mensajeRepository.save(mensaje);
    }
}
