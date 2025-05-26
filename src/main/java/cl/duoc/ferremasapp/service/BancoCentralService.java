package cl.duoc.ferremasapp.service;

import cl.duoc.ferremasapp.dto.DivisaDTO;
import java.math.BigDecimal;
import java.util.List;

public interface BancoCentralService {
    List<DivisaDTO> obtenerTasasDeCambio();
    
    DivisaDTO obtenerTasaDeCambio(String codigoDivisa);
    
    BigDecimal convertirDivisaAClp(BigDecimal monto, String codigoDivisa);
    
    BigDecimal convertirClpADivisa(BigDecimal monto, String codigoDivisa);
}