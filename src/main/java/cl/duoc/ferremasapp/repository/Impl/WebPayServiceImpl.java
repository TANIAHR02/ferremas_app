package cl.duoc.ferremasapp.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cl.duoc.ferremasapp.config.WebPayConfig;
import cl.duoc.ferremasapp.dto.PagoDTO;
import cl.duoc.ferremasapp.dto.PagoResponseDTO;
import cl.duoc.ferremasapp.service.WebPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class WebPayServiceImpl implements WebPayService {
    
    private final RestTemplate restTemplate;
    private final WebPayConfig webPayConfig;
    private final ObjectMapper objectMapper;
    
    @Autowired
    public WebPayServiceImpl(RestTemplate restTemplate, WebPayConfig webPayConfig) {
        this.restTemplate = restTemplate;
        this.webPayConfig = webPayConfig;
        this.objectMapper = new ObjectMapper();
    }
    
    @Override
    public PagoResponseDTO iniciarPago(Integer pedidoId, BigDecimal monto, String returnUrl) {
        try {
            String url = webPayConfig.getApiUrl() + "/transactions";
            
            HttpHeaders headers = getHeaders();
            
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("buy_order", "FERREMAS-" + pedidoId);
            requestBody.put("session_id", "SESS-" + pedidoId);
            requestBody.put("amount", monto);
            requestBody.put("return_url", returnUrl);
            
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
            
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    request,
                    String.class
            );
            
            if (response.getStatusCode() == HttpStatus.OK) {
                JsonNode jsonResponse = objectMapper.readTree(response.getBody());
                
                PagoResponseDTO pagoResponse = new PagoResponseDTO();
                pagoResponse.setToken(jsonResponse.get("token").asText());
                pagoResponse.setUrl(jsonResponse.get("url").asText());
                pagoResponse.setEstado("INICIADO");
                
                return pagoResponse;
            } else {
                throw new RuntimeException("Error al iniciar pago con WebPay: " + response.getBody());
            }
            
        } catch (Exception e) {
            throw new RuntimeException("Error al procesar pago con WebPay", e);
        }
    }
    
    @Override
    public PagoResponseDTO confirmarPago(String token) {
        try {
            String url = webPayConfig.getApiUrl() + "/transactions/" + token;
            
            HttpHeaders headers = getHeaders();
            HttpEntity<String> request = new HttpEntity<>(headers);
            
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.PUT,
                    request,
                    String.class
            );
            
            if (response.getStatusCode() == HttpStatus.OK) {
                JsonNode jsonResponse = objectMapper.readTree(response.getBody());
                
                PagoResponseDTO pagoResponse = new PagoResponseDTO();
                pagoResponse.setToken(token);
                pagoResponse.setEstado(jsonResponse.get("status").asText());
                pagoResponse.setOrdenCompra(jsonResponse.get("buy_order").asText());
                pagoResponse.setMonto(new BigDecimal(jsonResponse.get("amount").asText()));
                
                return pagoResponse;
            } else {
                throw new RuntimeException("Error al confirmar pago con WebPay: " + response.getBody());
            }
            
        } catch (Exception e) {
            throw new RuntimeException("Error al confirmar pago con WebPay", e);
        }
    }
    
    @Override
    public PagoResponseDTO estadoPago(String token) {
        try {
            String url = webPayConfig.getApiUrl() + "/transactions/" + token;
            
            HttpHeaders headers = getHeaders();
            HttpEntity<String> request = new HttpEntity<>(headers);
            
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    String.class
            );
            
            if (response.getStatusCode() == HttpStatus.OK) {
                JsonNode jsonResponse = objectMapper.readTree(response.getBody());
                
                PagoResponseDTO pagoResponse = new PagoResponseDTO();
                pagoResponse.setToken(token);
                pagoResponse.setEstado(jsonResponse.get("status").asText());
                pagoResponse.setOrdenCompra(jsonResponse.get("buy_order").asText());
                pagoResponse.setMonto(new BigDecimal(jsonResponse.get("amount").asText()));
                
                return pagoResponse;
            } else {
                throw new RuntimeException("Error al consultar estado del pago con WebPay: " + response.getBody());
            }
            
        } catch (Exception e) {
            throw new RuntimeException("Error al consultar estado del pago con WebPay", e);
        }
    }
    
    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Tbk-Api-Key-Id", webPayConfig.getCommerceCode());
        headers.set("Tbk-Api-Key-Secret", webPayConfig.getApiKey());
        return headers;
    }
}