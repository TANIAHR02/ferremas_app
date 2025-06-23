package cl.duoc.ferremasapp.controller;

import cl.duoc.ferremasapp.dto.PagoDTO;
import cl.duoc.ferremasapp.dto.PagoResponseDTO;
import cl.duoc.ferremasapp.service.WebPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/webpay")
public class WebPayController {

    @Autowired
    private WebPayService webPayService;

    @PostMapping("/iniciar")
    public ResponseEntity<PagoResponseDTO> iniciarPago(@RequestBody PagoDTO pagoDTO) {
        PagoResponseDTO response = webPayService.iniciarPago(pagoDTO);
        
        if (response.getError() != null) {
            return ResponseEntity.badRequest().body(response);
        }
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/return")
    public ResponseEntity<Map<String, Object>> procesarRetorno(@RequestParam("token_ws") String token) {
        // Aquí procesarías el token de retorno de WebPay
        // y verificarías el estado de la transacción
        
        Map<String, Object> response = Map.of(
            "status", "success",
            "message", "Pago procesado exitosamente",
            "token", token
        );
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status/{token}")
    public ResponseEntity<Map<String, Object>> consultarEstado(@PathVariable String token) {
        // Aquí consultarías el estado de la transacción en Transbank
        
        Map<String, Object> response = Map.of(
            "status", "authorized",
            "amount", 153474,
            "order_id", "12345",
            "transaction_date", "2024-06-22T18:30:00"
        );
        
        return ResponseEntity.ok(response);
    }
}
