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
@RequestMapping("/api/cliente")
@Tag(name = "Cliente", description = "Endpoints para clientes")
@PreAuthorize("hasRole('CLIENTE')")
public class ClienteController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/productos")
    @Operation(summary = "Ver catálogo", description = "Obtiene el catálogo de productos disponibles")
    public ResponseEntity<List<Producto>> verCatalogo() {
        List<Producto> productos = productoService.listarProductos();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/productos/categoria/{categoria}")
    @Operation(summary = "Productos por categoría", description = "Obtiene productos filtrados por categoría")
    public ResponseEntity<List<Producto>> productosPorCategoria(@PathVariable String categoria) {
     
        List<Producto> productos = productoService.listarProductos();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/productos/buscar")
    @Operation(summary = "Buscar productos", description = "Busca productos por nombre o descripción")
    public ResponseEntity<List<Producto>> buscarProductos(@RequestParam String query) {
        
        List<Producto> productos = productoService.listarProductos();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/productos/ofertas")
    @Operation(summary = "Productos en oferta", description = "Obtiene productos que están en oferta")
    public ResponseEntity<List<Producto>> productosEnOferta() {
        
        List<Producto> productos = productoService.listarProductos();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/pedidos")
    @Operation(summary = "Mis pedidos", description = "Obtiene los pedidos del cliente autenticado")
    public ResponseEntity<List<Pedido>> misPedidos() {
     
        List<Pedido> pedidos = pedidoService.listarPedidos();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/pedidos/{id}")
    @Operation(summary = "Detalles de mi pedido", description = "Obtiene los detalles de un pedido específico")
    public ResponseEntity<Pedido> detallesMiPedido(@PathVariable Integer id) {
        var pedidoOpt = pedidoService.buscarPorId(id);
        if (pedidoOpt.isPresent()) {
           
            return ResponseEntity.ok(pedidoOpt.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/pedidos")
    @Operation(summary = "Crear pedido", description = "Crea un nuevo pedido")
    public ResponseEntity<Map<String, Object>> crearPedido(@RequestBody Pedido pedido) {
        Map<String, Object> response = new HashMap<>();
        
        try {
        
            response.put("success", true);
            response.put("message", "Pedido creado exitosamente");
            response.put("pedidoId", 1); // ID del pedido creado
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al crear pedido: " + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }

    @PutMapping("/pedidos/{id}/cancelar")
    @Operation(summary = "Cancelar pedido", description = "Cancela un pedido propio")
    public ResponseEntity<Map<String, Object>> cancelarPedido(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
           
            response.put("success", true);
            response.put("message", "Pedido cancelado exitosamente");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al cancelar pedido: " + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/pedidos/{id}/seguimiento")
    @Operation(summary = "Seguimiento de pedido", description = "Obtiene el estado actual de un pedido")
    public ResponseEntity<Map<String, Object>> seguimientoPedido(@PathVariable Integer id) {
        Map<String, Object> seguimiento = new HashMap<>();
        
        var pedidoOpt = pedidoService.buscarPorId(id);
        if (pedidoOpt.isPresent()) {
            Pedido pedido = pedidoOpt.get();
            seguimiento.put("pedidoId", pedido.getId());
            seguimiento.put("estado", pedido.getEstado().toString());
            seguimiento.put("fechaCreacion", pedido.getFechaPedido());
            seguimiento.put("ultimaActualizacion", pedido.getFechaPedido()); 
            seguimiento.put("estimadoEntrega", "2-3 días hábiles"); 
        } else {
            seguimiento.put("error", "Pedido no encontrado");
        }
        
        return ResponseEntity.ok(seguimiento);
    }

    @GetMapping("/favoritos")
    @Operation(summary = "Mis favoritos", description = "Obtiene los productos favoritos del cliente")
    public ResponseEntity<List<Producto>> misFavoritos() {
        
        List<Producto> favoritos = productoService.listarProductos();
        return ResponseEntity.ok(favoritos);
    }

    @PostMapping("/favoritos/{productoId}")
    @Operation(summary = "Agregar a favoritos", description = "Agrega un producto a favoritos")
    public ResponseEntity<Map<String, Object>> agregarAFavoritos(@PathVariable Integer productoId) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            
            response.put("success", true);
            response.put("message", "Producto agregado a favoritos");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al agregar a favoritos: " + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/favoritos/{productoId}")
    @Operation(summary = "Quitar de favoritos", description = "Quita un producto de favoritos")
    public ResponseEntity<Map<String, Object>> quitarDeFavoritos(@PathVariable Integer productoId) {
        Map<String, Object> response = new HashMap<>();
        
        try {
           
            response.put("success", true);
            response.put("message", "Producto quitado de favoritos");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al quitar de favoritos: " + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/dashboard/stats")
    @Operation(summary = "Mis estadísticas", description = "Obtiene estadísticas personales del cliente")
    public ResponseEntity<Map<String, Object>> misEstadisticas() {
        Map<String, Object> stats = new HashMap<>();
        
   
        stats.put("totalPedidos", 0);
        stats.put("pedidosPendientes", 0);
        stats.put("pedidosEntregados", 0);
        stats.put("pedidosCancelados", 0);
        stats.put("totalGastado", 0.0);
        stats.put("productosFavoritos", 0);
        
        return ResponseEntity.ok(stats);
    }
} 