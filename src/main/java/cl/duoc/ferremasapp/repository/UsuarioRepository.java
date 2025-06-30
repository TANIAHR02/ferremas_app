package cl.duoc.ferremasapp.repository;

import cl.duoc.ferremasapp.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByEmail(String email);
    
    // Buscar por rol
    @Query("SELECT u FROM Usuario u WHERE u.rol.nombre = :rol")
    List<Usuario> findByRolNombre(@Param("rol") String rol);
    
    // Buscar usuarios activos
    List<Usuario> findByEstadoTrue();
    
    // Buscar por nombre (búsqueda parcial)
    List<Usuario> findByNombreContainingIgnoreCase(String nombre);
    
    // Buscar por email y estado
    Usuario findByEmailAndEstadoTrue(String email);
    
    // Contar usuarios por rol
    @Query("SELECT COUNT(u) FROM Usuario u WHERE u.rol.nombre = :rol")
    long countByRol(@Param("rol") String rol);
}
