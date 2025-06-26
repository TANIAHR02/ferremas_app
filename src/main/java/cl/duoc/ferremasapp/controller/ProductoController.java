package cl.duoc.ferremasapp.controller;

import cl.duoc.ferremasapp.dto.ActualizarPrecioDTO;
import cl.duoc.ferremasapp.model.HistorialPrecio;
import cl.duoc.ferremasapp.model.Producto;
import cl.duoc.ferremasapp.service.HistorialPrecioService;
import cl.duoc.ferremasapp.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;
    
    @Autowired
    private HistorialPrecioService historialPrecioService;

    @GetMapping
    public List<Producto> listarProductos() {
        return productoService.listarProductos();
    }

    @PostMapping
    public Producto agregarProducto(@RequestBody Producto producto) {
        return productoService.registrarProducto(producto);
    }

    @DeleteMapping("/{id}")
    public void eliminarProducto(@PathVariable Integer id) {
        productoService.eliminarProducto(id);
    }
    
    /**
     * Actualiza el precio de un producto usando PATCH
     * @param id ID del producto
     * @param actualizarPrecioDTO DTO con el nuevo precio y motivo
     * @return Respuesta con el historial de precio actualizado
     */
    @PatchMapping("/{id}/precio")
    public ResponseEntity<Map<String, Object>> actualizarPrecioProducto(
            @PathVariable Integer id,
            @RequestBody ActualizarPrecioDTO actualizarPrecioDTO) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Buscar el producto por ID
            var productoOpt = productoService.buscarPorId(id);
            if (productoOpt.isEmpty()) {
                response.put("success", false);
                response.put("message", "Producto no encontrado");
                response.put("error", "PRODUCT_NOT_FOUND");
                return ResponseEntity.notFound().build();
            }
            
            Producto producto = productoOpt.get();
            
            // Validar que el nuevo precio sea válido
            if (actualizarPrecioDTO.getNuevoPrecio() == null || 
                actualizarPrecioDTO.getNuevoPrecio().compareTo(BigDecimal.ZERO) <= 0) {
                response.put("success", false);
                response.put("message", "El precio debe ser mayor a 0");
                response.put("error", "INVALID_PRICE");
                return ResponseEntity.badRequest().body(response);
            }
            
            // Obtener precio actual para comparación
            HistorialPrecio precioActual = historialPrecioService
                .findPrecioActualByProductoCodigo(producto.getCodigo());
            
            BigDecimal precioAnterior = precioActual != null ? precioActual.getPrecio() : BigDecimal.ZERO;
            
            // Actualizar el precio en el historial
            HistorialPrecio nuevoPrecio = historialPrecioService
                .actualizarPrecio(producto.getCodigo(), actualizarPrecioDTO.getNuevoPrecio());
            
            // Construir respuesta
            response.put("success", true);
            response.put("message", "Precio actualizado exitosamente");
            response.put("producto", Map.of(
                "id", producto.getId(),
                "codigo", producto.getCodigo(),
                "nombre", producto.getNombre()
            ));
            response.put("precioAnterior", precioAnterior);
            response.put("precioNuevo", actualizarPrecioDTO.getNuevoPrecio());
            response.put("variacion", actualizarPrecioDTO.getNuevoPrecio().subtract(precioAnterior));
            response.put("motivo", actualizarPrecioDTO.getMotivo());
            response.put("usuario", actualizarPrecioDTO.getUsuario());
            response.put("fechaActualizacion", nuevoPrecio.getFechaInicio());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al actualizar el precio: " + e.getMessage());
            response.put("error", "UPDATE_ERROR");
            return ResponseEntity.internalServerError().body(response);
        }
    }
    
    /**
     * Actualiza el precio de un producto por código usando PATCH
     * @param codigo Código del producto
     * @param actualizarPrecioDTO DTO con el nuevo precio y motivo
     * @return Respuesta con el historial de precio actualizado
     */
    @PatchMapping("/codigo/{codigo}/precio")
    public ResponseEntity<Map<String, Object>> actualizarPrecioProductoPorCodigo(
            @PathVariable String codigo,
            @RequestBody ActualizarPrecioDTO actualizarPrecioDTO) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Buscar el producto por código
            var productoOpt = productoService.buscarPorCodigo(codigo);
            if (productoOpt.isEmpty()) {
                response.put("success", false);
                response.put("message", "Producto no encontrado con código: " + codigo);
                response.put("error", "PRODUCT_NOT_FOUND");
                return ResponseEntity.notFound().build();
            }
            
            Producto producto = productoOpt.get();
            
            // Validar que el nuevo precio sea válido
            if (actualizarPrecioDTO.getNuevoPrecio() == null || 
                actualizarPrecioDTO.getNuevoPrecio().compareTo(BigDecimal.ZERO) <= 0) {
                response.put("success", false);
                response.put("message", "El precio debe ser mayor a 0");
                response.put("error", "INVALID_PRICE");
                return ResponseEntity.badRequest().body(response);
            }
            
            // Obtener precio actual para comparación
            HistorialPrecio precioActual = historialPrecioService
                .findPrecioActualByProductoCodigo(codigo);
            
            BigDecimal precioAnterior = precioActual != null ? precioActual.getPrecio() : BigDecimal.ZERO;
            
            // Actualizar el precio en el historial
            HistorialPrecio nuevoPrecio = historialPrecioService
                .actualizarPrecio(codigo, actualizarPrecioDTO.getNuevoPrecio());
            
            // Construir respuesta
            response.put("success", true);
            response.put("message", "Precio actualizado exitosamente");
            response.put("producto", Map.of(
                "id", producto.getId(),
                "codigo", producto.getCodigo(),
                "nombre", producto.getNombre()
            ));
            response.put("precioAnterior", precioAnterior);
            response.put("precioNuevo", actualizarPrecioDTO.getNuevoPrecio());
            response.put("variacion", actualizarPrecioDTO.getNuevoPrecio().subtract(precioAnterior));
            response.put("motivo", actualizarPrecioDTO.getMotivo());
            response.put("usuario", actualizarPrecioDTO.getUsuario());
            response.put("fechaActualizacion", nuevoPrecio.getFechaInicio());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al actualizar el precio: " + e.getMessage());
            response.put("error", "UPDATE_ERROR");
            return ResponseEntity.internalServerError().body(response);
        }
    }
    
    /**
     * Obtiene el historial de precios de un producto
     * @param id ID del producto
     * @return Lista de historial de precios
     */
    @GetMapping("/{id}/historial-precios")
    public ResponseEntity<List<HistorialPrecio>> obtenerHistorialPrecios(@PathVariable Integer id) {
        var productoOpt = productoService.buscarPorId(id);
        if (productoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        List<HistorialPrecio> historial = historialPrecioService
            .findByProductoCodigo(productoOpt.get().getCodigo());
        
        return ResponseEntity.ok(historial);
    }
    
    /**
     * Obtiene el historial de precios de un producto por código
     * @param codigo Código del producto
     * @return Lista de historial de precios
     */
    @GetMapping("/codigo/{codigo}/historial-precios")
    public ResponseEntity<List<HistorialPrecio>> obtenerHistorialPreciosPorCodigo(@PathVariable String codigo) {
        List<HistorialPrecio> historial = historialPrecioService.findByProductoCodigo(codigo);
        return ResponseEntity.ok(historial);
    }
}
