package cl.duoc.ferremasapp.controller;

import cl.duoc.ferremasapp.model.Usuario;
import cl.duoc.ferremasapp.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<Usuario> register(@RequestBody Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return ResponseEntity.ok(usuarioService.registrarUsuario(usuario));
    }

    @PostMapping("/change-password")
    public ResponseEntity<Usuario> changePassword(@RequestParam String email, @RequestParam String newPassword) {
        return usuarioService.buscarPorEmail(email)
                .map(usuario -> {
                    usuario.setPassword(passwordEncoder.encode(newPassword));
                    usuarioService.actualizarUsuario(usuario);
                    return ResponseEntity.ok(usuario);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/success")
    public String loginSuccess() {
        return "Inicio de sesión exitoso";
    }

    @GetMapping("/logout-success")
    public String logoutSuccess() {
        return "Cierre de sesión exitoso";
    }
}
