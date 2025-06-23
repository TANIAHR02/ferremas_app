package cl.duoc.ferremasapp.service.Impl;

import cl.duoc.ferremasapp.dto.PagoDTO;
import cl.duoc.ferremasapp.dto.PagoResponseDTO;
import cl.duoc.ferremasapp.service.WebPayService;
import cl.duoc.ferremasapp.config.WebPayConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class WebPayServiceImpl implements WebPayService {

    private static final Logger logger = LoggerFactory.getLogger(WebPayServiceImpl.class);

    @Autowired
    private WebPayConfig webPayConfig;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public PagoResponseDTO iniciarPago(PagoDTO pagoDTO) {
        PagoResponseDTO responseDTO = new PagoResponseDTO();
        
        try {
            // Generar token único para la transacción
            String token = UUID.randomUUID().toString();
            
            // Preparar datos para Transbank
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("amount", pagoDTO.getMonto());
            requestBody.put("buy_order", pagoDTO.getPedidoId().toString());
            requestBody.put("session_id", token);
            requestBody.put("return_url", webPayConfig.getReturnUrl());
            
            // Configurar headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Tbk-Api-Key-Id", webPayConfig.getApiKeyId());
            headers.set("Tbk-Api-Key-Secret", webPayConfig.getApiKeySecret());
            
            // Crear request
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
            
            logger.info("Iniciando pago WebPay - Monto: {}, Orden: {}", pagoDTO.getMonto(), pagoDTO.getPedidoId());
            
            // Realizar llamada a Transbank
            @SuppressWarnings("unchecked")
            ResponseEntity<Map<String, Object>> response = restTemplate.postForEntity(
                webPayConfig.getApiUrl() + "/rswebpaytransaction/api/webpay/v1.2/transactions", 
                request, 
                (Class<Map<String, Object>>) (Class<?>) Map.class
            );
            
            // Procesar respuesta
            Map<String, Object> responseBody = response.getBody();
            if (responseBody != null && response.getStatusCode() == HttpStatus.OK) {
                responseDTO.setToken((String) responseBody.get("token"));
                responseDTO.setUrl((String) responseBody.get("url"));
                responseDTO.setMonto(pagoDTO.getMonto());
                responseDTO.setOrdenCompra(pagoDTO.getPedidoId().toString());
                
                logger.info("Pago WebPay iniciado exitosamente - Token: {}", responseDTO.getToken());
            } else {
                logger.error("Error en respuesta de Transbank - Status: {}", response.getStatusCode());
                responseDTO.setError("Error al procesar el pago con Transbank");
            }
            
        } catch (Exception e) {
            logger.error("Error al iniciar pago WebPay", e);
            responseDTO.setError("Error interno del servidor: " + e.getMessage());
        }
        
        return responseDTO;
    }

    @Override
    public PagoResponseDTO consultarEstadoTransaccion(String token) {
        PagoResponseDTO responseDTO = new PagoResponseDTO();
        try {
            String url = webPayConfig.getApiUrl() + "/rswebpaytransaction/api/webpay/v1.2/transactions/" + token;
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Tbk-Api-Key-Id", webPayConfig.getApiKeyId());
            headers.set("Tbk-Api-Key-Secret", webPayConfig.getApiKeySecret());

            HttpEntity<Void> request = new HttpEntity<>(headers);
            @SuppressWarnings("unchecked")
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(url, HttpMethod.GET, request, (Class<Map<String, Object>>) (Class<?>) Map.class);

            Map<String, Object> responseBody = response.getBody();
            if (responseBody != null && response.getStatusCode() == HttpStatus.OK) {
                responseDTO.setToken(token);
                if (responseBody.get("amount") != null)
                    responseDTO.setMonto(Double.parseDouble(responseBody.get("amount").toString()));
                if (responseBody.get("buy_order") != null)
                    responseDTO.setOrdenCompra(responseBody.get("buy_order").toString());
                // Puedes agregar más campos según la respuesta de Transbank
            } else {
                responseDTO.setError("No se pudo obtener el estado de la transacción");
            }
        } catch (Exception e) {
            responseDTO.setError("Error al consultar el estado: " + e.getMessage());
        }
        return responseDTO;
    }
}
