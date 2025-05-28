package cl.duoc.ferremasapp.service;

import cl.duoc.ferremasapp.model.Producto;
import cl.duoc.ferremasapp.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    public Optional<Producto> findById(Long id) {
        return productoRepository.findById(id);
    }

    public Producto findByCodigo(String codigo) {
        return productoRepository.findByCodigo(codigo);
    }
}
