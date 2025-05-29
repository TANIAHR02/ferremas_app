package cl.duoc.ferremasapp.controller;

import cl.duoc.ferremasapp.service.BancoCentralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/banco-central")
public class BancoCentralController {

    @Autowired
    private BancoCentralService bancoCentralService;

    @GetMapping("/tasa-cambio")
    public String obtenerTasaCambio() {
        return bancoCentralService.obtenerTasaCambio(); // Retorna el String directo
    }

    @GetMapping("/convertir")
    public BigDecimal convertirMonto(
            @RequestParam BigDecimal montoExtranjero,
            @RequestParam BigDecimal tasaCambio) {
        return bancoCentralService.convertirMonto(montoExtranjero, tasaCambio);
    }
}