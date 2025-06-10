package cl.duoc.ferremasapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.ferremasapp.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    Optional<Producto> findByCodigo(String codigo);
}
