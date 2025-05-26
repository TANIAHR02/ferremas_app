package cl.duoc.ferremasapp.repository;

import cl.duoc.ferremasapp.model.HistorialPrecio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HistorialPrecioRepository extends JpaRepository<HistorialPrecio, Integer> {
    List<HistorialPrecio> findByProductoCodigo(String codigoProducto);
    
    @Query("SELECT h FROM HistorialPrecio h WHERE h.producto.codigo = :codigoProducto AND h.fechaInicio <= :fecha AND (h.fechaFin IS NULL OR h.fechaFin >= :fecha)")
    List<HistorialPrecio> findByProductoCodigoAndFecha(String codigoProducto, LocalDateTime fecha);
    
    @Query("SELECT h FROM HistorialPrecio h WHERE h.producto.codigo = :codigoProducto AND h.fechaFin IS NULL")
    HistorialPrecio findPrecioActualByProductoCodigo(String codigoProducto);
}