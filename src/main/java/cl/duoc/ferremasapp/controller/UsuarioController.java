package cl.duoc.ferremasapp.controller;

import cl.duoc.ferremasapp.model.Usuario;
import cl.duoc.ferremasapp.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import cl.duoc.ferremasapp.repository.RolRepository;
import cl.duoc.ferremasapp.model.Rol;
import java.util.List;

import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolRepository rolRepository;

    @GetMapping("/{id}")
    public Optional<Usuario> obtenerUsuario(@PathVariable Integer id) {
        return usuarioService.buscarPorId(id);
    }

    @GetMapping("/roles")
    public List<Rol> obtenerRoles() {
        return rolRepository.findAll();
    }
}
