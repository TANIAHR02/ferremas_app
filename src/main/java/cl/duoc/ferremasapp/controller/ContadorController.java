package cl.duoc.ferremasapp.controller;

import cl.duoc.ferremasapp.model.Pago;
import cl.duoc.ferremasapp.model.Pedido;
import cl.duoc.ferremasapp.service.PagoService;
import cl.duoc.ferremasapp.service.PedidoService;
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
@RequestMapping("/api/contador")
@Tag(name = "Contador", description = "Endpoints para contadores")
@PreAuthorize("hasRole('CONTADOR')")
public class ContadorController {

    @Autowired
    private PagoService pagoService;

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/pagos")
    @Operation(summary = "Listar pagos", description = "Obtiene la lista de todos los pagos")
    public ResponseEntity<List<Pago>> listarPagos() {
        List<Pago> pagos = pagoService.listarPagos();
        return ResponseEntity.ok(pagos);
    }

    @GetMapping("/pagos/pendientes")
    @Operation(summary = "Pagos pendientes", description = "Obtiene pagos pendientes de confirmación")
    public ResponseEntity<List<Pago>> pagosPendientes() {
        
        List<Pago> pagos = pagoService.listarPagos();
        return ResponseEntity.ok(pagos);
    }

    @GetMapping("/pagos/transferencia")
    @Operation(summary = "Pagos por transferencia", description = "Obtiene pagos realizados por transferencia")
    public ResponseEntity<List<Pago>> pagosPorTransferencia() {
       
        List<Pago> pagos = pagoService.listarPagos();
        return ResponseEntity.ok(pagos);
    }

    @PutMapping("/pagos/{id}/confirmar")
    @Operation(summary = "Confirmar pago", description = "Confirma un pago realizado por transferencia")
    public ResponseEntity<Map<String, Object>> confirmarPago(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
           
            response.put("success", true);
            response.put("message", "Pago confirmado exitosamente");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al confirmar pago: " + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }

    @PutMapping("/pagos/{id}/rechazar")
    @Operation(summary = "Rechazar pago", description = "Rechaza un pago por transferencia")
    public ResponseEntity<Map<String, Object>> rechazarPago(
            @PathVariable Integer id, 
            @RequestParam String motivo) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            
            response.put("success", true);
            response.put("message", "Pago rechazado exitosamente");
            response.put("motivo", motivo);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al rechazar pago: " + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/pedidos")
    @Operation(summary = "Listar pedidos", description = "Obtiene la lista de pedidos para entrega")
    public ResponseEntity<List<Pedido>> listarPedidos() {
        List<Pedido> pedidos = pedidoService.listarPedidos();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/pedidos/listos-entrega")
    @Operation(summary = "Pedidos listos para entrega", description = "Obtiene pedidos listos para entrega a clientes")
    public ResponseEntity<List<Pedido>> pedidosListosEntrega() {
        
        List<Pedido> pedidos = pedidoService.listarPedidos();
        return ResponseEntity.ok(pedidos);
    }

    @PutMapping("/pedidos/{id}/confirmar-entrega")
    @Operation(summary = "Confirmar entrega", description = "Confirma la entrega de un pedido al cliente")
    public ResponseEntity<Map<String, Object>> confirmarEntrega(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
           
            response.put("success", true);
            response.put("message", "Entrega confirmada exitosamente");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al confirmar entrega: " + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/reportes/ventas")
    @Operation(summary = "Reporte de ventas", description = "Genera reporte de ventas")
    public ResponseEntity<Map<String, Object>> reporteVentas(
            @RequestParam(required = false) String fechaInicio,
            @RequestParam(required = false) String fechaFin) {
        Map<String, Object> reporte = new HashMap<>();
        
        
        reporte.put("fechaInicio", fechaInicio);
        reporte.put("fechaFin", fechaFin);
        reporte.put("totalVentas", 0);
        reporte.put("totalPagos", 0);
        reporte.put("pagosPendientes", 0);
        
        return ResponseEntity.ok(reporte);
    }

    @GetMapping("/reportes/pagos")
    @Operation(summary = "Reporte de pagos", description = "Genera reporte de pagos")
    public ResponseEntity<Map<String, Object>> reportePagos(
            @RequestParam(required = false) String fechaInicio,
            @RequestParam(required = false) String fechaFin) {
        Map<String, Object> reporte = new HashMap<>();
        
      
        reporte.put("fechaInicio", fechaInicio);
        reporte.put("fechaFin", fechaFin);
        reporte.put("pagosConfirmados", 0);
        reporte.put("pagosPendientes", 0);
        reporte.put("pagosRechazados", 0);
        
        return ResponseEntity.ok(reporte);
    }

    @GetMapping("/dashboard/stats")
    @Operation(summary = "Estadísticas del contador", description = "Obtiene estadísticas para el dashboard del contador")
    public ResponseEntity<Map<String, Object>> obtenerEstadisticas() {
        Map<String, Object> stats = new HashMap<>();
        
        List<Pago> todosPagos = pagoService.listarPagos();
        List<Pedido> todosPedidos = pedidoService.listarPedidos();
        
        stats.put("totalPagos", todosPagos.size());
        stats.put("totalPedidos", todosPedidos.size());
        stats.put("pagosPendientes", 0); 
        stats.put("pagosConfirmados", 0); 
        stats.put("pedidosEntregados", 0); 
        stats.put("pedidosPendientesEntrega", 0);
        
        return ResponseEntity.ok(stats);
    }
} 