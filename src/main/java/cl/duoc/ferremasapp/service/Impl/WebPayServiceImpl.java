package cl.duoc.ferremasapp.service.Impl;

import cl.duoc.ferremasapp.dto.PagoDTO;
import cl.duoc.ferremasapp.dto.PagoResponseDTO;
import cl.duoc.ferremasapp.service.WebPayService;
import cl.duoc.ferremasapp.config.WebPayConfig;
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
        String url = webPayConfig.getApiUrl();
        PagoResponseDTO responseDTO = new PagoResponseDTO();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            Map<String, Object> body = new HashMap<>();
            body.put("amount", pagoDTO.getMonto());
            body.put("buy_order", pagoDTO.getPedidoId());

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
            @SuppressWarnings("rawtypes")
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

            @SuppressWarnings("unchecked")
            Map<String, Object> responseBody = response.getBody();
            if (responseBody != null) {
                responseDTO.setToken((String) responseBody.get("token"));
                responseDTO.setUrl((String) responseBody.get("url"));
                responseDTO.setMonto(pagoDTO.getMonto());
                responseDTO.setOrdenCompra(pagoDTO.getPedidoId().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseDTO;
    }
}
