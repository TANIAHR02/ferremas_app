package cl.duoc.ferremasapp.service;

import java.util.List;
import java.util.Optional;

import cl.duoc.ferremasapp.model.Usuario;

public interface UsuarioService {
    void registrarUsuario(Usuario usuario);
    Optional<Usuario> buscarPorId(Integer id);
    Usuario buscarPorEmail(String email);
    List<Usuario> listarUsuarios();
    Usuario actualizarUsuario(Usuario usuario);
    void eliminarUsuario(Integer id);
}
