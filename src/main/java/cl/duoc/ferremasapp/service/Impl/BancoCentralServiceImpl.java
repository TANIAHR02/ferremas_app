package cl.duoc.ferremasapp.service.impl;

import cl.duoc.ferremasapp.dto.DivisaDTO;
import cl.duoc.ferremasapp.service.BancoCentralService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
public class BancoCentralServiceImpl implements BancoCentralService {

    // Datos de ejemplo - reemplazar con conexión real a API o base de datos
    private final List<DivisaDTO> divisas = Arrays.asList(
        new DivisaDTO("USD", BigDecimal.valueOf(950.50)),
        new DivisaDTO("EUR", BigDecimal.valueOf(1050.25)),
        new DivisaDTO("CLP", BigDecimal.ONE)
    );

    @Override
    public String obtenerTasaCambio() {
        // Obtiene la tasa del USD como ejemplo
        DivisaDTO usd = obtenerTasaDeCambio("USD");
        return "1 " + usd.getCodigo() + " = " + usd.getTasaCambio() + " CLP";
    }

    @Override
    public List<DivisaDTO> obtenerTasasDeCambio() {
        return divisas;
    }

    @Override
    public BigDecimal convertirMonto(BigDecimal monto, BigDecimal tasaCambio) {
        if (monto == null || tasaCambio == null) {
            throw new IllegalArgumentException("Monto y tasa de cambio no pueden ser nulos");
        }
        return monto.multiply(tasaCambio);
    }

    @Override
    public DivisaDTO obtenerTasaDeCambio(String codigoMoneda) {
        return divisas.stream()
            .filter(d -> d.getCodigo().equalsIgnoreCase(codigoMoneda))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Divisa no encontrada: " + codigoMoneda));
    }
}