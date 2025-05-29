package cl.duoc.ferremasapp.controller;

import cl.duoc.ferremasapp.dto.PagoResponseDTO;
import cl.duoc.ferremasapp.service.WebPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/webpay")
public class WebPayController {

    @Autowired
    private WebPayService webPayService;

    @PostMapping("/iniciar")
    public PagoResponseDTO iniciarPago(@RequestParam double monto) {
        return webPayService.iniciarPago(monto);
    }

    @PostMapping("/confirmar/{token}")
    public PagoResponseDTO confirmarPago(@PathVariable String token) {
        return webPayService.confirmarPago(token);
    }

    @PostMapping("/estado/{token}")
    public PagoResponseDTO estadoPago(@PathVariable String token) {
        return webPayService.estadoPago(token);
    }

    @PostMapping("/cancelar/{token}")
    public PagoResponseDTO cancelarPago(@PathVariable String token) {
        return webPayService.cancelarPago(token);
    }
}
