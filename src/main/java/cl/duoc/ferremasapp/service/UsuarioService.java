package cl.duoc.ferremasapp.service;

import java.util.List;
import java.util.Optional;

import cl.duoc.ferremasapp.model.Usuario;

public interface UsuarioService {
    Usuario registrarUsuario(Usuario usuario);
    Optional<Usuario> buscarPorId(Long id);
    List<Usuario> listarUsuarios();
    Usuario actualizarUsuario(Usuario usuario);
    void eliminarUsuario(Long id);
}
