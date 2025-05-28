package cl.duoc.ferremasapp.service;

import cl.duoc.ferremasapp.model.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    Usuario registrarUsuario(Usuario u);
    Optional<Usuario> buscarPorId(Long id);
    List<Usuario> listarUsuarios();
    Usuario actualizarUsuario(Usuario u);
    void eliminarUsuario(Long id);
    Optional<Usuario> buscarPorEmail(String email);
}
