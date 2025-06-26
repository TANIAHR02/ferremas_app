package cl.duoc.ferremasapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/health")
    public Map<String, Object> healthCheck() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "OK");
        response.put("message", "Aplicación funcionando correctamente");
        response.put("timestamp", System.currentTimeMillis());
        return response;
    }

    @GetMapping("/webpay-simulation-status")
    public Map<String, Object> webpaySimulationStatus() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "OK");
        response.put("message", "Endpoint de simulación WebPay disponible");
        response.put("endpoints", new String[]{
            "GET /api/webpay-simulation/simulate/scenario",
            "POST /api/webpay-simulation/simulate/init",
            "GET /api/webpay-simulation/simulate/transactions",
            "GET /api/webpay-simulation/simulate/panel"
        });
        return response;
    }

    @GetMapping("/routes")
    public Map<String, Object> getAvailableRoutes() {
        Map<String, Object> routes = new HashMap<>();
        routes.put("auth", "/api/auth/login");
        routes.put("usuarios", "/api/usuarios/{id}");
        routes.put("productos", "/api/productos");
        routes.put("pedidos", "/api/pedidos");
        routes.put("pagos", "/api/pagos");
        routes.put("webpay", "/api/webpay/iniciar");
        routes.put("mensajes", "/api/mensajes");
        routes.put("banco-central", "/api/banco-central/tasa-cambio");
        routes.put("swagger", "/swagger-ui.html");
        return routes;
    }
} 