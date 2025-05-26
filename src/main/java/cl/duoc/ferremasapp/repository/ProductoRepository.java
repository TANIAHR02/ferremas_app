package cl.duoc.ferremasapp.repository;

import cl.duoc.ferremasapp.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    Optional<Producto> findByCodigo(String codigo);
    
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
    
    List<Producto> findByCategoriaNombre(String categoriaNombre);
    
    @Query("SELECT p FROM Producto p JOIN p.stockItems s WHERE s.cantidad <= :cantidadMinima")
    List<Producto> findByStockBajo(int cantidadMinima);
}