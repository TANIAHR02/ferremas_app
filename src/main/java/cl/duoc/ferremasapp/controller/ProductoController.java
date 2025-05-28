package cl.duoc.ferremasapp.controller;

import cl.duoc.ferremasapp.dto.ProductoDTO;
import cl.duoc.ferremasapp.dto.PrecioDTO;
import cl.duoc.ferremasapp.model.HistorialPrecio;
import cl.duoc.ferremasapp.model.Producto;
import cl.duoc.ferremasapp.model.Stock;
import cl.duoc.ferremasapp.service.HistorialPrecioService;
import cl.duoc.ferremasapp.service.ProductoServ;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/productos")
@Tag(name = "Productos", description = "API para la gestión de productos")
public class ProductoController {

    private final ProductoService productoService;
    private final HistorialPrecioService historialPrecioService;

    @Autowired
    public ProductoController(ProductoService productoService, HistorialPrecioService historialPrecioService) {
        this.productoService = productoService;
        this.historialPrecioService = historialPrecioService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los productos")
    public ResponseEntity<List<Producto>> getAllProductos() {
        return ResponseEntity.ok(productoService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID")
    public ResponseEntity<Producto> getProductoById(@PathVariable Integer id) {
        return productoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/codigo/{codigo}")
    @Operation(summary = "Obtener producto por código")
    public ResponseEntity<Producto> getProductoByCodigo(@PathVariable String codigo) {
        return productoService.findByCodigo(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
