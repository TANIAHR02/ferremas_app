package cl.duoc.ferremasapp.repository.Impl;

import cl.duoc.ferremasapp.model.Producto;
import cl.duoc.ferremasapp.repository.ProductoRepository;
import cl.duoc.ferremasapp.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService {
    
    private final ProductoRepository productoRepository;
    
    @Autowired
    public ProductoServiceImpl(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }
    
    @Override
    public List<Producto> findAll() {
        return productoRepository.findAll();
    }
    
    @Override
    public Optional<Producto> findById(Integer id) {
        return productoRepository.findById(id);
    }
    
    @Override
    public Optional<Producto> findByCodigo(String codigo) {
        return productoRepository.findByCodigo(codigo);
    }
    
    @Override
    public List<Producto> findByNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }
    
    @Override
    public List<Producto> findByCategoria(String categoriaNombre) {
        return productoRepository.findByCategoriaNombre(categoriaNombre);
    }
    
    @Override
    public List<Producto> findByStockBajo(int cantidadMinima) {
        return productoRepository.findByStockBajo(cantidadMinima);
    }
    
    @Override
    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }
    
    @Override
    public void deleteById(Integer id) {
        productoRepository.deleteById(id);
    }
}