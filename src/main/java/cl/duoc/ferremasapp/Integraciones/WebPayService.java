package cl.duoc.ferremasapp.Integraciones;

import org.springframework.stereotype.Service;

@Service
public class WebPayService {

    public String iniciarPago(double monto) {
        // Simulación de inicio de pago (en un entorno real usarías Transbank SDK)
        return "Pago iniciado exitosamente. Monto: " + monto;
    }

    public String confirmarPago(String transaccionId) {
        return "Pago confirmado para la transacción: " + transaccionId;
    }

    public String cancelarPago(String transaccionId) {
        return "Pago cancelado para la transacción: " + transaccionId;
    }

    public String estadoPago(String transaccionId) {
        return "Estado del pago para transacción " + transaccionId + ": COMPLETADO";
    }
}
