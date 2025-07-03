package cl.duoc.ferremasapp.controller;

import cl.duoc.ferremasapp.dto.AuthRequestDTO;
import cl.duoc.ferremasapp.dto.AuthResponseDTO;
import cl.duoc.ferremasapp.dto.RegistroRequestDTO;
import cl.duoc.ferremasapp.model.Rol;
import cl.duoc.ferremasapp.model.Usuario;
import cl.duoc.ferremasapp.service.UsuarioService;
import cl.duoc.ferremasapp.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "Endpoints para autenticación y registro de usuarios")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Autentica un usuario y devuelve un token JWT")
    public ResponseEntity<?> login(@RequestBody AuthRequestDTO authRequest) {
        try {
            Usuario usuario = usuarioService.buscarPorEmail(authRequest.getEmail());
            
            if (usuario != null && passwordEncoder.matches(authRequest.getPassword(), usuario.getPassword())) {
                String token = jwtUtil.generateToken(usuario.getEmail(), usuario.getRol().getNombre());
                
                AuthResponseDTO response = new AuthResponseDTO();
                response.setSuccess(true);
                response.setMessage("Login exitoso");
                response.setToken(token);
                response.setUsuario(usuario);
                response.setTimestamp(LocalDateTime.now());
                
                return ResponseEntity.ok(response);
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Credenciales inválidas");
                return ResponseEntity.badRequest().body(error);
            }
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error en la autenticación: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PostMapping("/registro")
    @Operation(summary = "Registrar usuario", description = "Registra un nuevo usuario en el sistema")
    public ResponseEntity<?> registro(@RequestBody RegistroRequestDTO registroRequest) {
        try {
            // Verificar si el email ya existe
            if (usuarioService.buscarPorEmail(registroRequest.getEmail()) != null) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "El email ya está registrado");
                return ResponseEntity.badRequest().body(error);
            }

            // Crear el usuario
            Usuario usuario = new Usuario();
            usuario.setNombre(registroRequest.getNombre());
            usuario.setApellido(registroRequest.getApellido());
            usuario.setEmail(registroRequest.getEmail());
            usuario.setPassword(registroRequest.getPassword());
            usuario.setTelefono(registroRequest.getTelefono());
            usuario.setDireccion(registroRequest.getDireccion());
            usuario.setFechaRegistro(LocalDateTime.now());
            usuario.setEstado(true);

            // Asignar rol
            Rol rol;
            if (registroRequest.getRolId() != null) {
                rol = usuarioService.obtenerRolPorId(registroRequest.getRolId());
                if (rol == null) {
                    rol = new Rol();
                    rol.setId(1); // CLIENTE por defecto
                    rol.setNombre("CLIENTE");
                }
            } else {
                rol = new Rol();
                rol.setId(1); // CLIENTE por defecto
                rol.setNombre("CLIENTE");
            }
            usuario.setRol(rol);

            // Guardar usuario
            Usuario usuarioGuardado = usuarioService.registrarUsuario(usuario);

            // Generar token
            String token = jwtUtil.generateToken(usuarioGuardado.getEmail(), usuarioGuardado.getRol().getNombre());

            AuthResponseDTO response = new AuthResponseDTO();
            response.setSuccess(true);
            response.setMessage("Usuario registrado exitosamente");
            response.setToken(token);
            response.setUsuario(usuarioGuardado);
            response.setTimestamp(LocalDateTime.now());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error en el registro: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}
