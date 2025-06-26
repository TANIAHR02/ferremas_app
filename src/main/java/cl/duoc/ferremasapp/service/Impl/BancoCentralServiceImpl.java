package cl.duoc.ferremasapp.service.Impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import cl.duoc.ferremasapp.dto.DivisaDTO;
import cl.duoc.ferremasapp.service.BancoCentralService;

@Service
public class BancoCentralServiceImpl implements BancoCentralService {

    @Value("${bcentral.api.url}")
    private String bancoCentralApiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    // Datos de respaldo en caso de que la API no esté disponible
    private final List<DivisaDTO> divisasRespaldo = Arrays.asList(
        new DivisaDTO("USD", BigDecimal.valueOf(950.50)),
        new DivisaDTO("EUR", BigDecimal.valueOf(1050.25)),
        new DivisaDTO("CLP", BigDecimal.ONE)
    );

    @Override
    public String obtenerTasaCambio() {
        try {
            // Obtener tasa del USD desde el Banco Central
            DivisaDTO usd = obtenerTasaDeCambio("USD");
            return "1 " + usd.getCodigo() + " = " + usd.getTasaCambio() + " CLP";
        } catch (Exception e) {
            // En caso de error, usar datos de respaldo
            DivisaDTO usdRespaldo = divisasRespaldo.stream()
                .filter(d -> d.getCodigo().equals("USD"))
                .findFirst()
                .orElse(new DivisaDTO("USD", BigDecimal.valueOf(950.50)));
            return "1 " + usdRespaldo.getCodigo() + " = " + usdRespaldo.getTasaCambio() + " CLP (Datos de respaldo)";
        }
    }

    @Override
    public List<DivisaDTO> obtenerTasasDeCambio() {
        try {
            // Intentar obtener tasas reales del Banco Central
            return obtenerTasasRealesDelBancoCentral();
        } catch (Exception e) {
            // En caso de error, devolver datos de respaldo
            return divisasRespaldo;
        }
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
        try {
            // Intentar obtener tasa real del Banco Central
            return obtenerTasaRealDelBancoCentral(codigoMoneda);
        } catch (Exception e) {
            // En caso de error, usar datos de respaldo
            return divisasRespaldo.stream()
                .filter(d -> d.getCodigo().equalsIgnoreCase(codigoMoneda))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Divisa no encontrada: " + codigoMoneda));
        }
    }

    private List<DivisaDTO> obtenerTasasRealesDelBancoCentral() {
        try {
            // Configurar headers para la petición
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Realizar petición al Banco Central
            @SuppressWarnings("unchecked")
            ResponseEntity<Map<String, Object>> response = restTemplate.getForEntity(bancoCentralApiUrl, (Class<Map<String, Object>>) (Class<?>) Map.class);
            
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Map<String, Object> responseBody = response.getBody();
                
                // Procesar respuesta del Banco Central
                // La estructura puede variar según la API, aquí un ejemplo genérico
                if (responseBody != null && responseBody.containsKey("Dolares")) {
                    Object dolaresObj = responseBody.get("Dolares");
                    if (dolaresObj instanceof Map) {
                        @SuppressWarnings("unchecked")
                        Map<String, Object> dolares = (Map<String, Object>) dolaresObj;
                        if (dolares.containsKey("valor")) {
                            BigDecimal tasaUSD = new BigDecimal(dolares.get("valor").toString());
                            
                            // Calcular EUR basado en una relación aproximada (esto puede variar)
                            BigDecimal tasaEUR = tasaUSD.multiply(BigDecimal.valueOf(1.1));
                            
                            return Arrays.asList(
                                new DivisaDTO("USD", tasaUSD),
                                new DivisaDTO("EUR", tasaEUR),
                                new DivisaDTO("CLP", BigDecimal.ONE)
                            );
                        }
                    }
                }
            }
            
            // Si no se puede procesar la respuesta, lanzar excepción
            throw new RuntimeException("No se pudo procesar la respuesta del Banco Central");
            
        } catch (Exception e) {
            throw new RuntimeException("Error al conectar con el Banco Central: " + e.getMessage());
        }
    }

    private DivisaDTO obtenerTasaRealDelBancoCentral(String codigoMoneda) {
        List<DivisaDTO> tasas = obtenerTasasRealesDelBancoCentral();
        return tasas.stream()
            .filter(d -> d.getCodigo().equalsIgnoreCase(codigoMoneda))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Divisa no encontrada: " + codigoMoneda));
    }
}