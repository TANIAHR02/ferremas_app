package cl.duoc.ferremasapp.service.Impl;

import cl.duoc.ferremasapp.model.Producto;
import cl.duoc.ferremasapp.repository.ProductoRepository;
import cl.duoc.ferremasapp.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public Producto registrarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public Optional<Producto> buscarPorId(Integer id) {
        return productoRepository.findById(id);
    }

    @Override
    public Optional<Producto> buscarPorCodigo(String codigo) {
    
        return productoRepository.findAll().stream()
                .filter(p -> p.getCodigo().equals(codigo))
                .findFirst();
    }

    @Override
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    @Override
    public Producto actualizarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public void eliminarProducto(Integer id) {
        productoRepository.deleteById(id);
    }
}
