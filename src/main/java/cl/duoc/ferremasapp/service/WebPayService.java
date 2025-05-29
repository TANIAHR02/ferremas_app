package cl.duoc.ferremasapp.service;

import cl.duoc.ferremasapp.dto.PagoDTO;
import cl.duoc.ferremasapp.dto.PagoResponseDTO;

public interface WebPayService {
    PagoResponseDTO iniciarPago(PagoDTO pagoDTO);
    PagoResponseDTO confirmarPago(String token);
    PagoResponseDTO estadoPago(String token);
    PagoResponseDTO cancelarPago(String token);
}
