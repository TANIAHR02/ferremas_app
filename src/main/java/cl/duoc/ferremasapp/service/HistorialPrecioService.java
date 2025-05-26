package cl.duoc.ferremasapp.service;

import cl.duoc.ferremasapp.model.HistorialPrecio;
import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;

public interface HistorialPrecioService {
    List<HistorialPrecio> findByProductoCodigo(String codigoProducto);
    
    HistorialPrecio findPrecioActualByProductoCodigo(String codigoProducto);
    
    HistorialPrecio actualizarPrecio(String codigoProducto, BigDecimal nuevoPrecio);
    
    List<HistorialPrecio> findByProductoCodigoAndFecha(String codigoProducto, LocalDateTime fecha);
}
