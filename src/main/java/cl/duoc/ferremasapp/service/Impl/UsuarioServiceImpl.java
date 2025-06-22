package cl.duoc.ferremasapp.service.Impl;

import cl.duoc.ferremasapp.model.Usuario;
import cl.duoc.ferremasapp.repository.UsuarioRepository;
import cl.duoc.ferremasapp.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Optional<Usuario> buscarPorId(Integer id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Usuario registrarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public List<Usuario> listarUsuarios() {
        //
        throw new UnsupportedOperationException("Unimplemented method 'listarUsuarios'");
    }

    @Override
    public Usuario actualizarUsuario(Usuario usuario) {
        
        throw new UnsupportedOperationException("Unimplemented method 'actualizarUsuario'");
    }

    @Override
    public void eliminarUsuario(Integer id) {
    
        throw new UnsupportedOperationException("Unimplemented method 'eliminarUsuario'");
    }

    

    // Otros métodos implementados...
}
