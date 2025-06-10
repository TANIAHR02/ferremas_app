package cl.duoc.ferremasapp.service;

import cl.duoc.ferremasapp.dto.PagoDTO;
import cl.duoc.ferremasapp.dto.PagoResponseDTO;

public interface WebPayService {
    PagoResponseDTO iniciarPago(PagoDTO pagoDTO);
}
