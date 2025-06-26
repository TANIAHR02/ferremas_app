package cl.duoc.ferremasapp.service;

import cl.duoc.ferremasapp.dto.PagoDTO;
import cl.duoc.ferremasapp.dto.PagoResponseDTO;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WebPaySimulationService {

    private static final Logger logger = LoggerFactory.getLogger(WebPaySimulationService.class);
    
    // Almacenar transacciones simuladas en memoria
    private final Map<String, SimulatedTransaction> transactions = new ConcurrentHashMap<>();
    
    // Configuración de simulación
    private boolean simulationMode = true;
    private String simulationScenario = "SUCCESS"; // Escenarios disponibles
    
    // Escenarios disponibles
    public static final String[] SCENARIOS = {
        "SUCCESS",                    // Pago exitoso
        "FAIL",                       // Pago fallido
        "TIMEOUT",                    // Timeout de conexión
        "INSUFFICIENT_FUNDS"          // Fondos insuficientes
    };
    
    public static class SimulatedTransaction {
        private String token;
        private String buyOrder;
        private double amount;
        private String status; // PENDING, AUTHORIZED, FAILED, TIMEOUT
        private String authorizationCode;
        private String lastDigits;
        private long createdAt;
        private String errorMessage;
        private String errorCode;
        private String cardType; // VISA, MASTERCARD
        private String paymentMethod; // CREDIT, DEBIT
        private String responseCode;
        
        public SimulatedTransaction(String token, String buyOrder, double amount) {
            this.token = token;
            this.buyOrder = buyOrder;
            this.amount = amount;
            this.status = "PENDING";
            this.createdAt = System.currentTimeMillis();
        }
        
        // Getters y Setters
        public String getToken() { return token; }
        public void setToken(String token) { this.token = token; }
        
        public String getBuyOrder() { return buyOrder; }
        public void setBuyOrder(String buyOrder) { this.buyOrder = buyOrder; }
        
        public double getAmount() { return amount; }
        public void setAmount(double amount) { this.amount = amount; }
        
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        
        public String getAuthorizationCode() { return authorizationCode; }
        public void setAuthorizationCode(String authorizationCode) { this.authorizationCode = authorizationCode; }
        
        public String getLastDigits() { return lastDigits; }
        public void setLastDigits(String lastDigits) { this.lastDigits = lastDigits; }
        
        public long getCreatedAt() { return createdAt; }
        public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }
        
        public String getErrorMessage() { return errorMessage; }
        public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
        
        public String getErrorCode() { return errorCode; }
        public void setErrorCode(String errorCode) { this.errorCode = errorCode; }
        
        public String getCardType() { return cardType; }
        public void setCardType(String cardType) { this.cardType = cardType; }
        
        public String getPaymentMethod() { return paymentMethod; }
        public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
        
        public String getResponseCode() { return responseCode; }
        public void setResponseCode(String responseCode) { this.responseCode = responseCode; }
    }
    
    /**
     * Inicia una transacción de pago simulada
     */
    public PagoResponseDTO iniciarPagoSimulado(PagoDTO pagoDTO) {
        PagoResponseDTO responseDTO = new PagoResponseDTO();
        
        try {
            // Generar token único
            String token = UUID.randomUUID().toString();
            
            // Crear transacción simulada
            SimulatedTransaction transaction = new SimulatedTransaction(
                token, 
                pagoDTO.getPedidoId().toString(), 
                pagoDTO.getMonto()
            );
            
            // Almacenar transacción
            transactions.put(token, transaction);
            
            // Configurar respuesta según el escenario
            responseDTO.setToken(token);
            responseDTO.setMonto(pagoDTO.getMonto());
            responseDTO.setOrdenCompra(pagoDTO.getPedidoId().toString());
            
            // URL de simulación (no redirige a Transbank real)
            responseDTO.setUrl("/api/webpay-simulation/simulate/" + token);
            
            logger.info("Transacción simulada iniciada - Token: {}, Monto: {}, Escenario: {}", 
                       token, pagoDTO.getMonto(), simulationScenario);
            
        } catch (Exception e) {
            logger.error("Error al iniciar pago simulado", e);
            responseDTO.setError("Error interno del servidor: " + e.getMessage());
        }
        
        return responseDTO;
    }
    
    /**
     * Simula el proceso de pago y retorna el resultado
     */
    public Map<String, Object> simularProcesoPago(String token) {
        Map<String, Object> result = new HashMap<>();
        
        SimulatedTransaction transaction = transactions.get(token);
        if (transaction == null) {
            result.put("error", "Transacción no encontrada");
            return result;
        }
        
        // Simular procesamiento según el escenario configurado
        switch (simulationScenario) {
            case "SUCCESS":
                simularPagoExitoso(transaction);
                break;
            case "FAIL":
                simularPagoFallido(transaction);
                break;
            case "TIMEOUT":
                simularTimeout(transaction);
                break;
            case "INSUFFICIENT_FUNDS":
                simularFondosInsuficientes(transaction);
                break;
            default:
                simularPagoExitoso(transaction);
        }
        
        // Construir respuesta
        result.put("token", transaction.getToken());
        result.put("buy_order", transaction.getBuyOrder());
        result.put("amount", transaction.getAmount());
        result.put("status", transaction.getStatus());
        result.put("authorization_code", transaction.getAuthorizationCode());
        result.put("last_digits", transaction.getLastDigits());
        result.put("error_message", transaction.getErrorMessage());
        result.put("error_code", transaction.getErrorCode());
        result.put("card_type", transaction.getCardType());
        result.put("payment_method", transaction.getPaymentMethod());
        result.put("response_code", transaction.getResponseCode());
        
        return result;
    }
    
    /**
     * Simula un pago exitoso
     */
    private void simularPagoExitoso(SimulatedTransaction transaction) {
        transaction.setStatus("AUTHORIZED");
        transaction.setAuthorizationCode("AUTH" + System.currentTimeMillis());
        transaction.setLastDigits("1234");
        transaction.setErrorMessage(null);
        transaction.setErrorCode(null);
        transaction.setCardType("VISA");
        transaction.setPaymentMethod("CREDIT");
        transaction.setResponseCode("00");
        
        logger.info("Pago simulado exitoso - Token: {}", transaction.getToken());
    }
    
    /**
     * Simula un pago fallido
     */
    private void simularPagoFallido(SimulatedTransaction transaction) {
        transaction.setStatus("FAILED");
        transaction.setAuthorizationCode(null);
        transaction.setLastDigits("1234");
        transaction.setErrorMessage("Transacción rechazada por el banco");
        transaction.setErrorCode("REJECTED");
        transaction.setCardType("VISA");
        transaction.setPaymentMethod("CREDIT");
        transaction.setResponseCode("05");
        
        logger.info("Pago simulado fallido - Token: {}", transaction.getToken());
    }
    
    /**
     * Simula un timeout
     */
    private void simularTimeout(SimulatedTransaction transaction) {
        transaction.setStatus("TIMEOUT");
        transaction.setAuthorizationCode(null);
        transaction.setLastDigits(null);
        transaction.setErrorMessage("Tiempo de espera agotado");
        transaction.setErrorCode("TIMEOUT");
        transaction.setCardType(null);
        transaction.setPaymentMethod(null);
        transaction.setResponseCode("68");
        
        logger.info("Pago simulado con timeout - Token: {}", transaction.getToken());
    }
    
    /**
     * Simula fondos insuficientes
     */
    private void simularFondosInsuficientes(SimulatedTransaction transaction) {
        transaction.setStatus("FAILED");
        transaction.setAuthorizationCode(null);
        transaction.setLastDigits("1234");
        transaction.setErrorMessage("Fondos insuficientes");
        transaction.setErrorCode("INSUFFICIENT_FUNDS");
        transaction.setCardType("VISA");
        transaction.setPaymentMethod("DEBIT");
        transaction.setResponseCode("51");
        
        logger.info("Pago simulado con fondos insuficientes - Token: {}", transaction.getToken());
    }
    
    /**
     * Consulta el estado de una transacción simulada
     */
    public Map<String, Object> consultarEstadoSimulado(String token) {
        Map<String, Object> result = new HashMap<>();
        
        SimulatedTransaction transaction = transactions.get(token);
        if (transaction == null) {
            result.put("error", "Transacción no encontrada");
            return result;
        }
        
        result.put("token", transaction.getToken());
        result.put("buy_order", transaction.getBuyOrder());
        result.put("amount", transaction.getAmount());
        result.put("status", transaction.getStatus());
        result.put("authorization_code", transaction.getAuthorizationCode());
        result.put("last_digits", transaction.getLastDigits());
        result.put("error_message", transaction.getErrorMessage());
        result.put("error_code", transaction.getErrorCode());
        result.put("card_type", transaction.getCardType());
        result.put("payment_method", transaction.getPaymentMethod());
        result.put("response_code", transaction.getResponseCode());
        result.put("created_at", transaction.getCreatedAt());
        
        return result;
    }
    
    /**
     * Configura el escenario de simulación
     */
    public void configurarEscenario(String scenario) {
        this.simulationScenario = scenario;
        logger.info("Escenario de simulación configurado: {}", scenario);
    }
    
    /**
     * Obtiene el escenario actual
     */
    public String obtenerEscenarioActual() {
        return simulationScenario;
    }
    
    /**
     * Obtiene todas las transacciones simuladas
     */
    public Map<String, SimulatedTransaction> obtenerTransacciones() {
        return new HashMap<>(transactions);
    }
    
    /**
     * Limpia todas las transacciones simuladas
     */
    public void limpiarTransacciones() {
        transactions.clear();
        logger.info("Todas las transacciones simuladas han sido limpiadas");
    }
    
    /**
     * Verifica si el modo simulación está activo
     */
    public boolean isSimulationMode() {
        return simulationMode;
    }
    
    /**
     * Activa o desactiva el modo simulación
     */
    public void setSimulationMode(boolean simulationMode) {
        this.simulationMode = simulationMode;
        logger.info("Modo simulación {}: {}", simulationMode ? "activado" : "desactivado", simulationMode);
    }
} 