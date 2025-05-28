package cl.duoc.ferremasapp.service;

import cl.duoc.ferremasapp.model.Producto;
import java.util.List;
import java.util.Optional;

public interface ProductoService {
    Producto registrarProducto(Producto p);
    Optional<Producto> buscarPorId(Long id);
    List<Producto> listarProductos();
    Producto actualizarProducto(Producto p);
    void eliminarProducto(Long id);
    List<Producto> buscarPorNombre(String nombre);
    List<Producto> buscarPorCategoria(String categoria);
    List<Producto> buscarPorStockMenorA(int cantidad);
}
