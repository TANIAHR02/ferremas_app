package cl.duoc.ferremasapp.controller;

import cl.duoc.ferremasapp.service.WebPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/webpay")
public class WebPayController {

    @Autowired
    private WebPayService webPayService;

    @PostMapping("/iniciar")
    public String iniciarPago(@RequestParam double monto) {
        return webPayService.iniciarPago(monto);
    }

    @PostMapping("/confirmar")
    public String confirmarPago(@RequestParam String transaccionId) {
        return webPayService.confirmarPago(transaccionId);
    }

    @PostMapping("/cancelar")
    public String cancelarPago(@RequestParam String transaccionId) {
        return webPayService.cancelarPago(transaccionId);
    }

    @GetMapping("/estado")
    public String estadoPago(@RequestParam String transaccionId) {
        return webPayService.estadoPago(transaccionId);
    }
}
