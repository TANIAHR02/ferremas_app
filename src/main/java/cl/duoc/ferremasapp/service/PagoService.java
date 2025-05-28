package cl.duoc.ferremasapp.service;

import cl.duoc.ferremasapp.model.Pago;
import java.util.List;
import java.util.Optional;

public interface PagoService {
    Pago registrarPago(Pago p);
    Optional<Pago> buscarPorId(Long id);
    List<Pago> listarPagos();
}
