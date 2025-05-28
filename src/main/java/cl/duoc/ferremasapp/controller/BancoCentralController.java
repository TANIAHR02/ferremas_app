package cl.duoc.ferremasapp.controller;

import cl.duoc.ferremasapp.service.BancoCentralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/banco-central")
public class BancoCentralController {

    @Autowired
    private BancoCentralService bancoCentralService;

    @GetMapping("/tasa-cambio")
    public String obtenerTasaCambio() {
        return bancoCentralService.obtenerTasaCambio();
    }

    @GetMapping("/convertir")
    public double convertirMonto(@RequestParam double montoExtranjero, @RequestParam double tasaCambio) {
        return bancoCentralService.convertirMonto(montoExtranjero, tasaCambio);
    }
}
