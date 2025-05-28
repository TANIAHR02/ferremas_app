package cl.duoc.ferremasapp.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BancoCentralService {

    private final String apiUrl = "https://api.sbif.cl/api-sbifv3/recursos_api/dolar?apikey=YOUR_API_KEY&formato=json";

    public String obtenerTasaCambio() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(apiUrl, String.class);
    }

    public double convertirMonto(double montoExtranjero, double tasaCambio) {
        return montoExtranjero * tasaCambio;
    }
}
