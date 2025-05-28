package cl.duoc.ferremasapp.service.Impl;

import cl.duoc.ferremasapp.model.Producto;
import cl.duoc.ferremasapp.repository.ProductoRepository;
import cl.duoc.ferremasapp.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public Producto registrarProducto(Producto p) {
        return productoRepository.save(p);
    }

    @Override
    public Optional<Producto> buscarPorId(Long id) {
        return productoRepository.findById(id);
    }

    @Override
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    @Override
    public Producto actualizarProducto(Producto p) {
        return productoRepository.save(p);
    }

    @Override
    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }

    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findAll().stream()
                .filter(p -> p.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Producto> buscarPorCategoria(String categoria) {
        return productoRepository.findAll().stream()
                .filter(p -> p.getCategoria().getNombre().equalsIgnoreCase(categoria))
                .collect(Collectors.toList());
    }

    @Override
    public List<Producto> buscarPorStockMenorA(int cantidad) {
        return productoRepository.findAll().stream()
                .filter(p -> p.getStock().stream().anyMatch(s -> s.getCantidad() < cantidad))
                .collect(Collectors.toList());
    }
}
