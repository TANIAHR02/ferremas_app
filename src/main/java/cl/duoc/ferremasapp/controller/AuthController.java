package cl.duoc.ferremasapp.controller;

import cl.duoc.ferremasapp.model.Usuario;
import cl.duoc.ferremasapp.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public Usuario login(@RequestBody Usuario usuario) {
        return usuarioService.buscarPorEmail(usuario.getEmail());
    }
}
