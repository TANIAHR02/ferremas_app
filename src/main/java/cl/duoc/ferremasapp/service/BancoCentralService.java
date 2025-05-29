package cl.duoc.ferremasapp.service;

import java.math.BigDecimal;
import java.util.List;

import cl.duoc.ferremasapp.dto.DivisaDTO;

public interface BancoCentralService {
    String obtenerTasaCambio();
    List<DivisaDTO> obtenerTasasDeCambio();
    BigDecimal convertirMonto(BigDecimal monto, BigDecimal tasaCambio);
    DivisaDTO obtenerTasaDeCambio(String codigoMoneda);
}