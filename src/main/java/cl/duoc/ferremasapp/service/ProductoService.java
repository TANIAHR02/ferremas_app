package cl.duoc.ferremasapp.service;

import java.util.List;
import java.util.Optional;

import cl.duoc.ferremasapp.model.Producto;

public interface ProductoService {
    Producto registrarProducto(Producto producto);
    Optional<Producto> buscarPorId(Long id);
    List<Producto> listarProductos();
    Producto actualizarProducto(Producto producto);
    void eliminarProducto(Long id);
}
