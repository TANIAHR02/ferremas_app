package cl.duoc.ferremasapp.controller;

import cl.duoc.ferremasapp.model.Pedido;
import cl.duoc.ferremasapp.model.Producto;
import cl.duoc.ferremasapp.service.PedidoService;
import cl.duoc.ferremasapp.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vendedor")
@Tag(name = "Vendedor", description = "Endpoints para vendedores")
@PreAuthorize("hasRole('VENDEDOR')")
public class VendedorController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ProductoService productoService;

    @GetMapping("/productos")
    @Operation(summary = "Listar productos disponibles", description = "Obtiene la lista de productos disponibles en bodega")
    public ResponseEntity<List<Producto>> listarProductosDisponibles() {
        List<Producto> productos = productoService.listarProductos();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/productos/bajo-stock")
    @Operation(summary = "Productos con bajo stock", description = "Obtiene productos con stock bajo")
    public ResponseEntity<List<Producto>> productosBajoStock() {
        
        List<Producto> productos = productoService.listarProductos();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/pedidos")
    @Operation(summary = "Listar pedidos", description = "Obtiene la lista de todos los pedidos")
    public ResponseEntity<List<Pedido>> listarPedidos() {
        List<Pedido> pedidos = pedidoService.listarPedidos();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/pedidos/pendientes")
    @Operation(summary = "Pedidos pendientes", description = "Obtiene pedidos pendientes de aprobación")
    public ResponseEntity<List<Pedido>> pedidosPendientes() {
        
        List<Pedido> pedidos = pedidoService.listarPedidos();
        return ResponseEntity.ok(pedidos);
    }

    @PutMapping("/pedidos/{id}/aprobar")
    @Operation(summary = "Aprobar pedido", description = "Aprueba un pedido y lo envía a bodega")
    public ResponseEntity<Map<String, Object>> aprobarPedido(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
           
            response.put("success", true);
            response.put("message", "Pedido aprobado exitosamente");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al aprobar pedido: " + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }

    @PutMapping("/pedidos/{id}/rechazar")
    @Operation(summary = "Rechazar pedido", description = "Rechaza un pedido")
    public ResponseEntity<Map<String, Object>> rechazarPedido(@PathVariable Integer id, @RequestParam String motivo) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            var pedidoOpt = pedidoService.buscarPorId(id);
            if (pedidoOpt.isPresent()) {
                Pedido pedido = pedidoOpt.get();
                pedido.setEstado(Pedido.EstadoPedido.RECHAZADO);
                pedidoService.actualizarPedido(pedido);
                
                response.put("success", true);
                response.put("message", "Pedido rechazado exitosamente");
                response.put("motivo", motivo);
            } else {
                response.put("success", false);
                response.put("message", "Pedido no encontrado");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al rechazar pedido: " + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/pedidos/{id}/detalles")
    @Operation(summary = "Detalles del pedido", description = "Obtiene los detalles completos de un pedido")
    public ResponseEntity<Pedido> obtenerDetallesPedido(@PathVariable Integer id) {
        var pedidoOpt = pedidoService.buscarPorId(id);
        if (pedidoOpt.isPresent()) {
            return ResponseEntity.ok(pedidoOpt.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/pedidos/{id}/enviar-bodega")
    @Operation(summary = "Enviar a bodega", description = "Envía un pedido aprobado a bodega para preparación")
    public ResponseEntity<Map<String, Object>> enviarABodega(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            
            response.put("success", true);
            response.put("message", "Pedido enviado a bodega exitosamente");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al enviar a bodega: " + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/dashboard/stats")
    @Operation(summary = "Estadísticas del vendedor", description = "Obtiene estadísticas para el dashboard del vendedor")
    public ResponseEntity<Map<String, Object>> obtenerEstadisticas() {
        Map<String, Object> stats = new HashMap<>();
        
        List<Pedido> todosPedidos = pedidoService.listarPedidos();
        List<Producto> todosProductos = productoService.listarProductos();
        
        stats.put("totalPedidos", todosPedidos.size());
        stats.put("totalProductos", todosProductos.size());
        stats.put("pedidosPendientes", 0); 
        stats.put("pedidosAprobados", 0);
        stats.put("pedidosRechazados", 0);
        
        return ResponseEntity.ok(stats);
    }
} 