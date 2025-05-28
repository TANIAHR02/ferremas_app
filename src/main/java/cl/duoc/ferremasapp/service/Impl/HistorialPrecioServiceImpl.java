package cl.duoc.ferremasapp.service.Impl;

import cl.duoc.ferremasapp.model.HistorialPrecio;
import cl.duoc.ferremasapp.model.Producto;
import cl.duoc.ferremasapp.repository.HistorialPrecioRepository;
import cl.duoc.ferremasapp.repository.ProductoRepository;
import cl.duoc.ferremasapp.service.HistorialPrecioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class HistorialPrecioServiceImpl implements HistorialPrecioService {
    
    private final HistorialPrecioRepository historialPrecioRepository;
    private final ProductoRepository productoRepository;
    
    @Autowired
    public HistorialPrecioServiceImpl(HistorialPrecioRepository historialPrecioRepository,
                                      ProductoRepository productoRepository) {
        this.historialPrecioRepository = historialPrecioRepository;
        this.productoRepository = productoRepository;
    }
    
    @Override
    public List<HistorialPrecio> findByProductoCodigo(String codigoProducto) {
        return historialPrecioRepository.findByProductoCodigo(codigoProducto);
    }
    
    @Override
    public HistorialPrecio findPrecioActualByProductoCodigo(String codigoProducto) {
        return historialPrecioRepository.findPrecioActualByProductoCodigo(codigoProducto);
    }
    
    @Override
    @Transactional
    public HistorialPrecio actualizarPrecio(String codigoProducto, BigDecimal nuevoPrecio) {
        Producto producto = productoRepository.findByCodigo(codigoProducto)
                .orElseThrow(() -> new NoSuchElementException("Producto no encontrado con código: " + codigoProducto));
        
        // Cerrar el precio actual
        HistorialPrecio precioActual = historialPrecioRepository.findPrecioActualByProductoCodigo(codigoProducto);
        if (precioActual != null) {
            precioActual.setFechaFin(LocalDateTime.now());
            historialPrecioRepository.save(precioActual);
        }
        
        // Crear nuevo registro de precio
        HistorialPrecio nuevoPrecioHistorial = new HistorialPrecio();
        nuevoPrecioHistorial.setProducto(producto);
        nuevoPrecioHistorial.setPrecio(nuevoPrecio);
        
        return historialPrecioRepository.save(nuevoPrecioHistorial);
    }
    
    @Override
    public List<HistorialPrecio> findByProductoCodigoAndFecha(String codigoProducto, LocalDateTime fecha) {
        return historialPrecioRepository.findByProductoCodigoAndFecha(codigoProducto, fecha);
    }
}