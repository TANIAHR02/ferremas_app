package cl.duoc.ferremasapp.controller;

import cl.duoc.ferremasapp.dto.PagoDTO;
import cl.duoc.ferremasapp.dto.PagoResponseDTO;
import cl.duoc.ferremasapp.service.WebPaySimulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/webpay-simulation")
public class WebPaySimulationController {

    @Autowired
    private WebPaySimulationService webPaySimulationService;

    /**
     * Inicia una transacción de pago simulada
     */
    @PostMapping("/simulate/init")
    @ResponseBody
    public ResponseEntity<PagoResponseDTO> iniciarPagoSimulado(@RequestBody PagoDTO pagoDTO) {
        PagoResponseDTO response = webPaySimulationService.iniciarPagoSimulado(pagoDTO);
        
        if (response.getError() != null) {
            return ResponseEntity.badRequest().body(response);
        }
        
        return ResponseEntity.ok(response);
    }

    /**
     * Simula el proceso de pago y muestra el resultado
     */
    @GetMapping("/simulate/{token}")
    public String simularProcesoPago(@PathVariable String token, Model model) {
        Map<String, Object> result = webPaySimulationService.simularProcesoPago(token);
        
        if (result.containsKey("error")) {
            model.addAttribute("status", "error");
            model.addAttribute("error", result.get("error"));
        } else {
            String status = (String) result.get("status");
            model.addAttribute("status", status.toLowerCase());
            model.addAttribute("token", result.get("token"));
            model.addAttribute("monto", result.get("amount"));
            model.addAttribute("orden", result.get("buy_order"));
            model.addAttribute("codigoAutorizacion", result.get("authorization_code"));
            model.addAttribute("ultimosDigitos", result.get("last_digits"));
            model.addAttribute("errorMessage", result.get("error_message"));
        }
        
        return "webpay-simulacion";
    }

    /**
     * Consulta el estado de una transacción simulada
     */
    @GetMapping("/simulate/status/{token}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> consultarEstadoSimulado(@PathVariable String token) {
        Map<String, Object> result = webPaySimulationService.consultarEstadoSimulado(token);
        
        if (result.containsKey("error")) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(result);
    }

    /**
     * Configura el escenario de simulación
     */
    @PostMapping("/simulate/scenario")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> configurarEscenario(@RequestBody Map<String, String> request) {
        String scenario = request.get("scenario");
        
        if (scenario == null || scenario.trim().isEmpty()) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Escenario no especificado");
            return ResponseEntity.badRequest().body(error);
        }
        
        webPaySimulationService.configurarEscenario(scenario);
        
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Escenario configurado exitosamente");
        response.put("scenario", webPaySimulationService.obtenerEscenarioActual());
        
        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene el escenario actual
     */
    @GetMapping("/simulate/scenario")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> obtenerEscenarioActual() {
        Map<String, Object> response = new HashMap<>();
        response.put("scenario", webPaySimulationService.obtenerEscenarioActual());
        response.put("simulationMode", webPaySimulationService.isSimulationMode());
        
        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene todos los escenarios disponibles
     */
    @GetMapping("/simulate/scenarios")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> obtenerEscenariosDisponibles() {
        Map<String, Object> response = new HashMap<>();
        response.put("scenarios", WebPaySimulationService.SCENARIOS);
        response.put("currentScenario", webPaySimulationService.obtenerEscenarioActual());
        response.put("count", WebPaySimulationService.SCENARIOS.length);
        
        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene todas las transacciones simuladas
     */
    @GetMapping("/simulate/transactions")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> obtenerTransacciones() {
        Map<String, Object> response = new HashMap<>();
        response.put("transactions", webPaySimulationService.obtenerTransacciones());
        response.put("count", webPaySimulationService.obtenerTransacciones().size());
        
        return ResponseEntity.ok(response);
    }

    /**
     * Limpia todas las transacciones simuladas
     */
    @DeleteMapping("/simulate/transactions")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> limpiarTransacciones() {
        webPaySimulationService.limpiarTransacciones();
        
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Todas las transacciones simuladas han sido limpiadas");
        
        return ResponseEntity.ok(response);
    }

    /**
     * Activa/desactiva el modo simulación
     */
    @PostMapping("/simulate/mode")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> configurarModoSimulacion(@RequestBody Map<String, Boolean> request) {
        Boolean enabled = request.get("enabled");
        
        if (enabled == null) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Parámetro 'enabled' no especificado");
            return ResponseEntity.badRequest().body(error);
        }
        
        webPaySimulationService.setSimulationMode(enabled);
        
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Modo simulación " + (enabled ? "activado" : "desactivado"));
        response.put("simulationMode", webPaySimulationService.isSimulationMode());
        
        return ResponseEntity.ok(response);
    }

    /**
     * Panel de control para pruebas de simulación
     */
    @GetMapping("/simulate/panel")
    public String panelControl(Model model) {
        model.addAttribute("currentScenario", webPaySimulationService.obtenerEscenarioActual());
        model.addAttribute("simulationMode", webPaySimulationService.isSimulationMode());
        model.addAttribute("transactions", webPaySimulationService.obtenerTransacciones());
        
        return "webpay-panel-control";
    }
} 