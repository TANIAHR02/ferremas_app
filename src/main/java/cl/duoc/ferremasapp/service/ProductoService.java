package cl.duoc.ferremasapp.service;

import java.util.List;
import java.util.Optional;

import cl.duoc.ferremasapp.model.Producto;

public interface ProductoService {
    Producto registrarProducto(Producto producto);
    Optional<Producto> buscarPorId(Integer id);
    Optional<Producto> buscarPorCodigo(String codigo); // Agregado
    List<Producto> listarProductos();  // Este es como findAll()
    Producto actualizarProducto(Producto producto);
    void eliminarProducto(Integer id);
}
