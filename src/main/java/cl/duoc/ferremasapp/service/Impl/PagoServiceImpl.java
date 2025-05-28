package cl.duoc.ferremasapp.service.impl;

import cl.duoc.ferremasapp.model.Pago;
import cl.duoc.ferremasapp.repository.PagoRepository;
import cl.duoc.ferremasapp.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PagoServiceImpl implements PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Override
    public Pago registrarPago(Pago p) {
        return pagoRepository.save(p);
    }

    @Override
    public Optional<Pago> buscarPorId(Long id) {
        return pagoRepository.findById(id);
    }

    @Override
    public List<Pago> listarPagos() {
        return pagoRepository.findAll();
    }
}
