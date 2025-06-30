package cl.duoc.ferremasapp.service;

public interface EmailService {
    void enviarEmailRegistro(String to, String nombre);
    void enviarEmailConfirmacionPedido(String to, String nombre, Integer pedidoId);
    void enviarEmailCambioEstadoPedido(String to, String nombre, Integer pedidoId, String estado);
    void enviarEmailPasswordTemporal(String to, String nombre, String passwordTemporal);
    void enviarEmailOferta(String to, String nombre, String oferta);
    void enviarEmailNotificacion(String to, String asunto, String contenido);
} 