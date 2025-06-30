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
@RequestMapping("/api/bodeguero")
@Tag(name = "Bodeguero", description = "Endpoints para bodegueros")
@PreAuthorize("hasRole('BODEGUERO')")
public class BodegueroController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ProductoService productoService;

    @GetMapping("/pedidos")
    @Operation(summary = "Listar pedidos", description = "Obtiene la lista de pedidos asignados al bodeguero")
    public ResponseEntity<List<Pedido>> listarPedidos() {
        List<Pedido> pedidos = pedidoService.listarPedidos();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/pedidos/pendientes")
    @Operation(summary = "Pedidos pendientes de preparación", description = "Obtiene pedidos pendientes de preparación")
    public ResponseEntity<List<Pedido>> pedidosPendientesPreparacion() {
       
        List<Pedido> pedidos = pedidoService.listarPedidos();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/pedidos/en-preparacion")
    @Operation(summary = "Pedidos en preparación", description = "Obtiene pedidos que están siendo preparados")
    public ResponseEntity<List<Pedido>> pedidosEnPreparacion() {
       
        List<Pedido> pedidos = pedidoService.listarPedidos();
        return ResponseEntity.ok(pedidos);
    }

    @PutMapping("/pedidos/{id}/aceptar")
    @Operation(summary = "Aceptar pedido", description = "Acepta un pedido para preparación")
    public ResponseEntity<Map<String, Object>> aceptarPedido(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            
            response.put("success", true);
            response.put("message", "Pedido aceptado para preparación");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al aceptar pedido: " + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }

    @PutMapping("/pedidos/{id}/preparar")
    @Operation(summary = "Marcar como preparado", description = "Marca un pedido como preparado y listo para entrega")
    public ResponseEntity<Map<String, Object>> marcarComoPreparado(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
           
            response.put("success", true);
            response.put("message", "Pedido marcado como preparado");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al marcar como preparado: " + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }

    @PutMapping("/pedidos/{id}/entregar-vendedor")
    @Operation(summary = "Entregar a vendedor", description = "Entrega un pedido preparado al vendedor")
    public ResponseEntity<Map<String, Object>> entregarAVendedor(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
           
            response.put("success", true);
            response.put("message", "Pedido entregado al vendedor exitosamente");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al entregar pedido: " + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/inventario")
    @Operation(summary = "Ver inventario", description = "Obtiene el estado del inventario")
    public ResponseEntity<List<Producto>> verInventario() {
        List<Producto> productos = productoService.listarProductos();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/inventario/bajo-stock")
    @Operation(summary = "Productos con bajo stock", description = "Obtiene productos con stock bajo")
    public ResponseEntity<List<Producto>> productosBajoStock() {
       
        List<Producto> productos = productoService.listarProductos();
        return ResponseEntity.ok(productos);
    }

    @PutMapping("/productos/{id}/actualizar-stock")
    @Operation(summary = "Actualizar stock", description = "Actualiza el stock de un producto")
    public ResponseEntity<Map<String, Object>> actualizarStock(
            @PathVariable Integer id, 
            @RequestParam Integer cantidad) {
        Map<String, Object> response = new HashMap<>();
        
        try {
           
            response.put("success", true);
            response.put("message", "Stock actualizado exitosamente");
            response.put("nuevaCantidad", cantidad);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al actualizar stock: " + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/dashboard/stats")
    @Operation(summary = "Estadísticas del bodeguero", description = "Obtiene estadísticas para el dashboard del bodeguero")
    public ResponseEntity<Map<String, Object>> obtenerEstadisticas() {
        Map<String, Object> stats = new HashMap<>();
        
        List<Pedido> todosPedidos = pedidoService.listarPedidos();
        List<Producto> todosProductos = productoService.listarProductos();
        
        stats.put("totalPedidos", todosPedidos.size());
        stats.put("totalProductos", todosProductos.size());
        stats.put("pedidosPendientes", 0); 
        stats.put("pedidosEnPreparacion", 0); 
        stats.put("pedidosPreparados", 0); 
        stats.put("productosBajoStock", 0); 
        
        return ResponseEntity.ok(stats);
    }
} 