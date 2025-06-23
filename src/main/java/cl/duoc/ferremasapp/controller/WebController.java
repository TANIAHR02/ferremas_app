package cl.duoc.ferremasapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String home() {
        return "home"; // Página de inicio
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registro")
    public String registro() {
        return "registro";
    }

    @GetMapping("/productos")
    public String productos() {
        return "productos";
    }

    @GetMapping("/carrito")
    public String carrito() {
        return "carrito";
    }

    @GetMapping("/perfil")
    public String perfil() {
        return "perfil";
    }

    @GetMapping("/quienes-somos")
    public String quienesSomos() {
        return "quienes-somos";
    }

    @GetMapping("/tipos-pago")
    public String tiposPago() {
        return "tipos-pago";
    }
} 