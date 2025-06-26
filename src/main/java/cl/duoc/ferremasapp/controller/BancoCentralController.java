package cl.duoc.ferremasapp.controller;

import cl.duoc.ferremasapp.dto.DivisaDTO;
import cl.duoc.ferremasapp.service.BancoCentralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/banco-central")
public class BancoCentralController {

    @Autowired
    private BancoCentralService bancoCentralService;

    @GetMapping("/tasa-cambio")
    public String obtenerTasaCambio() {
        return bancoCentralService.obtenerTasaCambio(); // Retorna el String directo
    }

    @GetMapping("/tasas-cambio")
    public List<DivisaDTO> obtenerTodasLasTasas() {
        return bancoCentralService.obtenerTasasDeCambio();
    }

    @GetMapping("/tasa-cambio/{codigoMoneda}")
    public DivisaDTO obtenerTasaPorMoneda(@PathVariable String codigoMoneda) {
        return bancoCentralService.obtenerTasaDeCambio(codigoMoneda);
    }

    @GetMapping("/convertir")
    public BigDecimal convertirMonto(
            @RequestParam BigDecimal montoExtranjero,
            @RequestParam BigDecimal tasaCambio) {
        return bancoCentralService.convertirMonto(montoExtranjero, tasaCambio);
    }

    @GetMapping("/convertir/{codigoMoneda}")
    public BigDecimal convertirMontoPorMoneda(
            @PathVariable String codigoMoneda,
            @RequestParam BigDecimal monto) {
        DivisaDTO divisa = bancoCentralService.obtenerTasaDeCambio(codigoMoneda);
        return bancoCentralService.convertirMonto(monto, divisa.getTasaCambio());
    }

    @GetMapping("/convertir-usd")
    public BigDecimal convertirUSD(@RequestParam BigDecimal montoUSD) {
        return convertirMontoPorMoneda("USD", montoUSD);
    }

    @GetMapping("/convertir-eur")
    public BigDecimal convertirEUR(@RequestParam BigDecimal montoEUR) {
        return convertirMontoPorMoneda("EUR", montoEUR);
    }
}