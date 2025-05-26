package cl.duoc.ferremasapp.service;

import cl.duoc.ferremasapp.model.Producto;
import java.util.List;
import java.util.Optional;

public interface ProductoService {
    List<Producto> findAll();
    
    Optional<Producto> findById(Integer id);
    
    Optional<Producto> findByCodigo(String codigo);
    
    List<Producto> findByNombre(String nombre);
    
    List<Producto> findByCategoria(String categoriaNombre);
    
    List<Producto> findByStockBajo(int cantidadMinima);
    
    Producto save(Producto producto);
    
    void deleteById(Integer id);
}