package cl.duoc.ferremasapp.service.Impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cl.duoc.ferremasapp.Integraciones.BancoCentralService;
import cl.duoc.ferremasapp.dto.DivisaDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class BancoCentralServiceImpl implements BancoCentralService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${bcentral.api.url}")
    private String apiUrl;

    public BancoCentralServiceImpl() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public List<DivisaDTO> obtenerTasaCambio() {
        List<DivisaDTO> tasas = new ArrayList<>();
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
            JsonNode root = objectMapper.readTree(response.getBody());
            JsonNode dolarNode = root.path("Dolares").get(0); // Ajusta según formato del JSON real

            BigDecimal valorDolar = new BigDecimal(dolarNode.path("Valor").asText()).setScale(2, RoundingMode.HALF_UP);
            DivisaDTO divisa = new DivisaDTO("USD", "Peso Chileno", valorDolar);
            tasas.add(divisa);

        } catch (Exception e) {
            System.out.println("Error al obtener tasa de cambio: " + e.getMessage());
        }
        return tasas;
    }

    @Override
    public BigDecimal convertirMonto(BigDecimal monto, BigDecimal tasaCambio) {
        return monto.multiply(tasaCambio).setScale(2, RoundingMode.HALF_UP);
    }
}
