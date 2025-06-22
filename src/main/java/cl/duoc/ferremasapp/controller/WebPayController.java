package cl.duoc.ferremasapp.controller;

import cl.duoc.ferremasapp.dto.PagoDTO;
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
    public PagoResponseDTO iniciarPago(@RequestBody PagoDTO pagoDTO) {
        return webPayService.iniciarPago(pagoDTO);
    }
}
