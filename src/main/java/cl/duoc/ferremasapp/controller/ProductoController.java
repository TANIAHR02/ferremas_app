package cl.duoc.ferremasapp.controller;

import cl.duoc.ferremasapp.model.Producto;
import cl.duoc.ferremasapp.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public List<Producto> listarProductos() {
        return productoService.listarProductos();
    }

    @PostMapping
    public Producto agregarProducto(@RequestBody Producto producto) {
        return productoService.guardarProducto(producto);
    }

    @DeleteMapping("/{id}")
    public void eliminarProducto(@PathVariable Integer id) {
        productoService.eliminarProducto(id);
    }
}
