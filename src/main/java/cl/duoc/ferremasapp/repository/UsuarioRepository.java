package cl.duoc.ferremasapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import cl.duoc.ferremasapp.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {}
