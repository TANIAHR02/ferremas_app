package cl.duoc.ferremasapp.service;

import cl.duoc.ferremasapp.dto.PagoDTO;

public interface WebPayService {
    String iniciarPago(PagoDTO pagoDTO);
    String confirmarPago(String transaccionId);
    String cancelarPago(String transaccionId);
    String estadoPago(String transaccionId);
}
