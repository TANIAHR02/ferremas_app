package cl.duoc.ferremasapp.controller;

import cl.duoc.ferremasapp.model.Pago;
import cl.duoc.ferremasapp.service.PagoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@Tag(name = "Pagos", description = "API para la gestión de pagos")
public class PagoController {

    private final PagoService pagoService;

    @Autowired
    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo pago")
    public ResponseEntity<Pago> registrar(@RequestBody Pago pago) {
        return ResponseEntity.ok(pagoService.registrarPago(pago));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener pago por ID")
    public ResponseEntity<Pago> buscarPorId(@PathVariable Integer id) {
        return pagoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Listar todos los pagos")
    public ResponseEntity<List<Pago>> listarTodos() {
        return ResponseEntity.ok(pagoService.listarPagos());
    }
}
