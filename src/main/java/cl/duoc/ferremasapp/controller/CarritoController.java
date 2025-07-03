package cl.duoc.ferremasapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.*;

@RestController
@RequestMapping("/api/carrito")
@Tag(name = "Carrito", description = "API para la gestión del carrito de compras")
@CrossOrigin(origins = "*")
public class CarritoController {

    // Simulación de carrito en memoria (en producción usarías una base de datos o Redis)
    private static final Map<String, List<Map<String, Object>>> carritos = new HashMap<>();

    @GetMapping("/{sessionId}")
    @Operation(summary = "Obtener carrito", description = "Obtiene los productos del carrito de una sesión")
    public ResponseEntity<Map<String, Object>> obtenerCarrito(@PathVariable String sessionId) {
        List<Map<String, Object>> items = carritos.getOrDefault(sessionId, new ArrayList<>());
        
        Map<String, Object> response = new HashMap<>();
        response.put("items", items);
        response.put("totalItems", items.size());
        response.put("subtotal", calcularSubtotal(items));
        response.put("envio", calcularEnvio(items));
        response.put("iva", calcularIVA(items));
        response.put("total", calcularTotal(items));
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{sessionId}/agregar")
    @Operation(summary = "Agregar producto al carrito", description = "Agrega un producto al carrito")
    public ResponseEntity<Map<String, Object>> agregarProducto(
            @PathVariable String sessionId,
            @RequestBody Map<String, Object> producto) {
        
        List<Map<String, Object>> items = carritos.getOrDefault(sessionId, new ArrayList<>());
        
        // Verificar si el producto ya existe en el carrito
        boolean productoExiste = false;
        for (Map<String, Object> item : items) {
            if (item.get("codigo").equals(producto.get("codigo"))) {
                int cantidadActual = (Integer) item.get("cantidad");
                item.put("cantidad", cantidadActual + 1);
                productoExiste = true;
                break;
            }
        }
        
        if (!productoExiste) {
            producto.put("cantidad", 1);
            items.add(producto);
        }
        
        carritos.put(sessionId, items);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Producto agregado al carrito");
        response.put("totalItems", items.size());
        
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{sessionId}/actualizar-cantidad")
    @Operation(summary = "Actualizar cantidad", description = "Actualiza la cantidad de un producto en el carrito")
    public ResponseEntity<Map<String, Object>> actualizarCantidad(
            @PathVariable String sessionId,
            @RequestParam String codigo,
            @RequestParam int cantidad) {
        
        List<Map<String, Object>> items = carritos.getOrDefault(sessionId, new ArrayList<>());
        
        for (Map<String, Object> item : items) {
            if (item.get("codigo").equals(codigo)) {
                if (cantidad <= 0) {
                    items.remove(item);
                } else {
                    item.put("cantidad", cantidad);
                }
                break;
            }
        }
        
        carritos.put(sessionId, items);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Cantidad actualizada");
        response.put("totalItems", items.size());
        
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{sessionId}/eliminar")
    @Operation(summary = "Eliminar producto", description = "Elimina un producto específico del carrito")
    public ResponseEntity<Map<String, Object>> eliminarProducto(
            @PathVariable String sessionId,
            @RequestParam String codigo) {
        
        List<Map<String, Object>> items = carritos.getOrDefault(sessionId, new ArrayList<>());
        
        items.removeIf(item -> item.get("codigo").equals(codigo));
        
        carritos.put(sessionId, items);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Producto eliminado del carrito");
        response.put("totalItems", items.size());
        
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{sessionId}/vaciar")
    @Operation(summary = "Vaciar carrito", description = "Elimina todos los productos del carrito")
    public ResponseEntity<Map<String, Object>> vaciarCarrito(@PathVariable String sessionId) {
        carritos.remove(sessionId);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Carrito vaciado");
        response.put("totalItems", 0);
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{sessionId}/resumen")
    @Operation(summary = "Resumen del carrito", description = "Obtiene el resumen de precios del carrito")
    public ResponseEntity<Map<String, Object>> obtenerResumen(@PathVariable String sessionId) {
        List<Map<String, Object>> items = carritos.getOrDefault(sessionId, new ArrayList<>());
        
        Map<String, Object> resumen = new HashMap<>();
        resumen.put("subtotal", calcularSubtotal(items));
        resumen.put("envio", calcularEnvio(items));
        resumen.put("iva", calcularIVA(items));
        resumen.put("total", calcularTotal(items));
        resumen.put("totalItems", items.size());
        
        return ResponseEntity.ok(resumen);
    }

    // Métodos auxiliares para cálculos
    private double calcularSubtotal(List<Map<String, Object>> items) {
        return items.stream()
                .mapToDouble(item -> {
                    double precio = ((Number) item.get("precio")).doubleValue();
                    int cantidad = (Integer) item.get("cantidad");
                    return precio * cantidad;
                })
                .sum();
    }

    private double calcularEnvio(List<Map<String, Object>> items) {
        double subtotal = calcularSubtotal(items);
        return subtotal > 50000 ? 0 : 5990; // Envío gratuito sobre $50.000
    }

    private double calcularIVA(List<Map<String, Object>> items) {
        double subtotal = calcularSubtotal(items);
        return subtotal * 0.19; // IVA 19%
    }

    private double calcularTotal(List<Map<String, Object>> items) {
        return calcularSubtotal(items) + calcularEnvio(items) + calcularIVA(items);
    }
} 