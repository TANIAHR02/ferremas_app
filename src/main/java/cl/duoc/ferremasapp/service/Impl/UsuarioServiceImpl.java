package cl.duoc.ferremasapp.service.Impl;

import cl.duoc.ferremasapp.model.Usuario;
import cl.duoc.ferremasapp.repository.UsuarioRepository;
import cl.duoc.ferremasapp.repository.RolRepository;
import cl.duoc.ferremasapp.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Usuario registrarUsuario(Usuario usuario) {
        // Encriptar password
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        
        // Establecer fecha de registro y estado
        usuario.setFechaRegistro(LocalDateTime.now());
        usuario.setEstado(true);
        
        return usuarioRepository.save(usuario);
    }

    @Override
    public Optional<Usuario> buscarPorId(Integer id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario actualizarUsuario(Usuario usuario) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(usuario.getId());
        if (usuarioExistente.isPresent()) {
            Usuario usuarioActual = usuarioExistente.get();
            
            // Actualizar campos permitidos
            usuarioActual.setNombre(usuario.getNombre());
            usuarioActual.setApellido(usuario.getApellido());
            usuarioActual.setTelefono(usuario.getTelefono());
            usuarioActual.setDireccion(usuario.getDireccion());
            
            // Solo actualizar password si se proporciona uno nuevo
            if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
                usuarioActual.setPassword(passwordEncoder.encode(usuario.getPassword()));
            }
            
            return usuarioRepository.save(usuarioActual);
        }
        throw new RuntimeException("Usuario no encontrado");
    }

    @Override
    public void eliminarUsuario(Integer id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public List<Usuario> buscarPorRol(String rol) {
        return usuarioRepository.findByRolNombre(rol);
    }

    @Override
    public boolean cambiarPassword(Integer usuarioId, String passwordActual, String passwordNuevo) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            
            // Verificar password actual
            if (passwordEncoder.matches(passwordActual, usuario.getPassword())) {
                usuario.setPassword(passwordEncoder.encode(passwordNuevo));
                usuarioRepository.save(usuario);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean resetPassword(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario != null) {
            
            String passwordTemporal = generarPasswordTemporal();
            usuario.setPassword(passwordEncoder.encode(passwordTemporal));
            usuarioRepository.save(usuario);
            
            
            return true;
        }
        return false;
    }

    @Override
    public List<Usuario> buscarUsuariosActivos() {
        return usuarioRepository.findByEstadoTrue();
    }

    @Override
    public boolean activarDesactivarUsuario(Integer usuarioId) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.setEstado(!usuario.getEstado());
            usuarioRepository.save(usuario);
            return true;
        }
        return false;
    }

    @Override
    public List<Usuario> buscarPorNombre(String nombre) {
        return usuarioRepository.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public cl.duoc.ferremasapp.model.Rol obtenerRolPorId(Long rolId) {
        return rolRepository.findById(rolId.intValue()).orElse(null);
    }

    private String generarPasswordTemporal() {
        
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int index = (int) (Math.random() * chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }
}
