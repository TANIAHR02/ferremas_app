package cl.duoc.ferremasapp.service;

import java.util.List;

import cl.duoc.ferremasapp.model.Usuario;

public interface UsuarioService {
    Usuario registrarUsuario(Usuario usuario);
    Usuario buscarPorEmail(String email);
    List<Usuario> listarUsuarios();
    Usuario actualizarUsuario(Usuario usuario);
    void eliminarUsuario(Integer id);
}
