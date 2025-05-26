package cl.duoc.ferremasapp.service;

import cl.duoc.ferremasapp.dto.PagoResponseDTO;
import cl.duoc.ferremasapp.dto.PagoDTO;
import java.math.BigDecimal;

public interface WebPayService {
    PagoResponseDTO iniciarPago(Integer pedidoId, BigDecimal monto, String returnUrl);
    
    PagoResponseDTO confirmarPago(String token);
    
    PagoResponseDTO estadoPago(String token);
}