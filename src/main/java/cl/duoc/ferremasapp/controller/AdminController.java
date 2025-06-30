package cl.duoc.ferremasapp.controller;

import cl.duoc.ferremasapp.model.Usuario;
import cl.duoc.ferremasapp.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Administración", description = "Endpoints para administradores")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/usuarios")
    @Operation(summary = "Listar usuarios", description = "Obtiene la lista de todos los usuarios del sistema")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/usuarios/activos")
    @Operation(summary = "Listar usuarios activos", description = "Obtiene la lista de usuarios activos")
    public ResponseEntity<List<Usuario>> listarUsuariosActivos() {
        List<Usuario> usuarios = usuarioService.buscarUsuariosActivos();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/usuarios/rol/{rol}")
    @Operation(summary = "Listar usuarios por rol", description = "Obtiene la lista de usuarios por rol específico")
    public ResponseEntity<List<Usuario>> listarUsuariosPorRol(@PathVariable String rol) {
        List<Usuario> usuarios = usuarioService.buscarPorRol(rol);
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/usuarios/buscar")
    @Operation(summary = "Buscar usuarios por nombre", description = "Busca usuarios por nombre")
    public ResponseEntity<List<Usuario>> buscarUsuariosPorNombre(@RequestParam String nombre) {
        List<Usuario> usuarios = usuarioService.buscarPorNombre(nombre);
        return ResponseEntity.ok(usuarios);
    }

    @PutMapping("/usuarios/{id}/estado")
    @Operation(summary = "Activar/Desactivar usuario", description = "Activa o desactiva un usuario")
    public ResponseEntity<Map<String, Object>> cambiarEstadoUsuario(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        
        boolean resultado = usuarioService.activarDesactivarUsuario(id);
        if (resultado) {
            response.put("success", true);
            response.put("message", "Estado del usuario actualizado exitosamente");
        } else {
            response.put("success", false);
            response.put("message", "No se pudo actualizar el estado del usuario");
        }
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/usuarios/{id}/reset-password")
    @Operation(summary = "Resetear password", description = "Resetea la contraseña de un usuario")
    public ResponseEntity<Map<String, Object>> resetPassword(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        
        var usuarioOpt = usuarioService.buscarPorId(id);
        if (usuarioOpt.isPresent()) {
            boolean resultado = usuarioService.resetPassword(usuarioOpt.get().getEmail());
            if (resultado) {
                response.put("success", true);
                response.put("message", "Password reseteado exitosamente. Se enviará un email con la nueva contraseña.");
            } else {
                response.put("success", false);
                response.put("message", "No se pudo resetear el password");
            }
        } else {
            response.put("success", false);
            response.put("message", "Usuario no encontrado");
        }
        
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/usuarios/{id}")
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario del sistema")
    public ResponseEntity<Map<String, Object>> eliminarUsuario(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            usuarioService.eliminarUsuario(id);
            response.put("success", true);
            response.put("message", "Usuario eliminado exitosamente");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al eliminar usuario: " + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/dashboard/stats")
    @Operation(summary = "Estadísticas del dashboard", description = "Obtiene estadísticas para el dashboard de administración")
    public ResponseEntity<Map<String, Object>> obtenerEstadisticas() {
        Map<String, Object> stats = new HashMap<>();
        
        List<Usuario> todosUsuarios = usuarioService.listarUsuarios();
        List<Usuario> usuariosActivos = usuarioService.buscarUsuariosActivos();
        
        stats.put("totalUsuarios", todosUsuarios.size());
        stats.put("usuariosActivos", usuariosActivos.size());
        stats.put("usuariosInactivos", todosUsuarios.size() - usuariosActivos.size());
        
        // Contar por roles
        stats.put("clientes", usuarioService.buscarPorRol("CLIENTE").size());
        stats.put("vendedores", usuarioService.buscarPorRol("VENDEDOR").size());
        stats.put("bodegueros", usuarioService.buscarPorRol("BODEGUERO").size());
        stats.put("contadores", usuarioService.buscarPorRol("CONTADOR").size());
        stats.put("admins", usuarioService.buscarPorRol("ADMIN").size());
        
        return ResponseEntity.ok(stats);
    }
} 