package cl.duoc.ferremasapp.service;

import java.util.List;
import java.util.Optional;

import cl.duoc.ferremasapp.model.Usuario;

public interface UsuarioService {
    Usuario registrarUsuario(Usuario usuario);
    Optional<Usuario> buscarPorId(Integer id);
    Usuario buscarPorEmail(String email);
    List<Usuario> listarUsuarios();
    Usuario actualizarUsuario(Usuario usuario);
    void eliminarUsuario(Integer id);
    
    // Nuevas funcionalidades
    List<Usuario> buscarPorRol(String rol);
    boolean cambiarPassword(Integer usuarioId, String passwordActual, String passwordNuevo);
    boolean resetPassword(String email);
    List<Usuario> buscarUsuariosActivos();
    boolean activarDesactivarUsuario(Integer usuarioId);
    List<Usuario> buscarPorNombre(String nombre);
}
