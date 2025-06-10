package cl.duoc.ferremasapp.controller;

import cl.duoc.ferremasapp.model.Pedido;
import cl.duoc.ferremasapp.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pedidos")
@Tag(name = "Pedidos", description = "API para la gestión de pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    @Autowired
    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo pedido")
    public ResponseEntity<Pedido> crear(@RequestBody Pedido pedido) {
        return ResponseEntity.ok(pedidoService.crearPedido(pedido));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener pedido por ID")
    public ResponseEntity<Pedido> obtenerPorId(@PathVariable Integer id) {
        return pedidoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Listar todos los pedidos")
    public ResponseEntity<List<Pedido>> listarTodos() {
        return ResponseEntity.ok(pedidoService.listarPedidos());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar pedido por ID")
    public ResponseEntity<Pedido> actualizar(@PathVariable Integer id, @RequestBody Pedido pedido) {
        pedido.setId(id);
        return ResponseEntity.ok(pedidoService.actualizarPedido(pedido));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar pedido por ID")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        pedidoService.eliminarPedido(id);
        return ResponseEntity.noContent().build();
    }
}
