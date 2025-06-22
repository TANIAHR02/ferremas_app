package cl.duoc.ferremasapp.service;

import java.util.List;
import java.util.Optional;

import cl.duoc.ferremasapp.model.Usuario;

public interface UsuarioService {
    Usuario registrarUsuario(Usuario usuario);
    Usuario buscarPorEmail(String email);
    Optional<Usuario> buscarPorId(Integer id);
    List<Usuario> listarUsuarios();
    Usuario actualizarUsuario(Usuario usuario);
    void eliminarUsuario(Integer id);
}
