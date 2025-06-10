package cl.duoc.ferremasapp.service.impl;

import cl.duoc.ferremasapp.dto.PagoDTO;
import cl.duoc.ferremasapp.dto.PagoResponseDTO;
import cl.duoc.ferremasapp.service.WebPayService;
import cl.duoc.ferremasapp.util.WebPayConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class WebPayServiceImpl implements WebPayService {

    @Autowired
    private WebPayConfig webPayConfig;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public PagoResponseDTO iniciarPago(PagoDTO pagoDTO) {
        String url = webPayConfig.getWebpayUrl();
        ObjectMapper mapper = new ObjectMapper();
        PagoResponseDTO responseDTO = new PagoResponseDTO();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            Map<String, Object> body = new HashMap<>();
            body.put("amount", pagoDTO.getMonto());
            body.put("buy_order", pagoDTO.getOrdenCompra());

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

            Map<String, Object> responseBody = response.getBody();
            if (responseBody != null) {
                responseDTO.setToken((String) responseBody.get("token"));
                responseDTO.setUrl((String) responseBody.get("url"));
                responseDTO.setMonto(pagoDTO.getMonto());
                responseDTO.setOrdenCompra(pagoDTO.getOrdenCompra());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseDTO;
    }
}
