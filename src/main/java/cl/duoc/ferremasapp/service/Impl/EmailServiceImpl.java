package cl.duoc.ferremasapp.service.Impl;

import cl.duoc.ferremasapp.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void enviarEmailRegistro(String to, String nombre) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("¡Bienvenido a FERREMAS!");
        message.setText(String.format(
            "Hola %s,\n\n" +
            "¡Gracias por registrarte en FERREMAS!\n\n" +
            "Tu cuenta ha sido creada exitosamente. Ahora puedes:\n" +
            "- Explorar nuestro catálogo de productos\n" +
            "- Realizar compras online\n" +
            "- Recibir ofertas especiales\n" +
            "- Hacer seguimiento de tus pedidos\n\n" +
            "¡Esperamos que disfrutes de nuestros productos!\n\n" +
            "Saludos,\n" +
            "Equipo FERREMAS",
            nombre
        ));
        
        try {
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Error enviando email de registro: " + e.getMessage());
        }
    }

    @Override
    public void enviarEmailConfirmacionPedido(String to, String nombre, Integer pedidoId) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Confirmación de Pedido - FERREMAS");
        message.setText(String.format(
            "Hola %s,\n\n" +
            "Tu pedido #%d ha sido confirmado y está siendo procesado.\n\n" +
            "Detalles del pedido:\n" +
            "- Número de pedido: %d\n" +
            "- Estado: Confirmado\n" +
            "- Próximo paso: Preparación en bodega\n\n" +
            "Te notificaremos cuando tu pedido esté listo para envío.\n\n" +
            "Gracias por tu compra,\n" +
            "Equipo FERREMAS",
            nombre, pedidoId, pedidoId
        ));
        
        try {
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Error enviando email de confirmación: " + e.getMessage());
        }
    }

    @Override
    public void enviarEmailCambioEstadoPedido(String to, String nombre, Integer pedidoId, String estado) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Actualización de Pedido - FERREMAS");
        message.setText(String.format(
            "Hola %s,\n\n" +
            "Tu pedido #%d ha cambiado de estado.\n\n" +
            "Nuevo estado: %s\n\n" +
            "Puedes hacer seguimiento de tu pedido en tu cuenta.\n\n" +
            "Saludos,\n" +
            "Equipo FERREMAS",
            nombre, pedidoId, estado
        ));
        
        try {
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Error enviando email de cambio de estado: " + e.getMessage());
        }
    }

    @Override
    public void enviarEmailPasswordTemporal(String to, String nombre, String passwordTemporal) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Nueva Contraseña Temporal - FERREMAS");
        message.setText(String.format(
            "Hola %s,\n\n" +
            "Se ha generado una nueva contraseña temporal para tu cuenta.\n\n" +
            "Contraseña temporal: %s\n\n" +
            "Por seguridad, te recomendamos cambiar esta contraseña al iniciar sesión.\n\n" +
            "Saludos,\n" +
            "Equipo FERREMAS",
            nombre, passwordTemporal
        ));
        
        try {
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Error enviando email de password temporal: " + e.getMessage());
        }
    }

    @Override
    public void enviarEmailOferta(String to, String nombre, String oferta) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("¡Oferta Especial - FERREMAS!");
        message.setText(String.format(
            "Hola %s,\n\n" +
            "¡Tenemos una oferta especial para ti!\n\n" +
            "%s\n\n" +
            "¡No te la pierdas! Visita nuestra tienda online.\n\n" +
            "Saludos,\n" +
            "Equipo FERREMAS",
            nombre, oferta
        ));
        
        try {
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Error enviando email de oferta: " + e.getMessage());
        }
    }

    @Override
    public void enviarEmailNotificacion(String to, String asunto, String contenido) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(asunto);
        message.setText(contenido);
        
        try {
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Error enviando email de notificación: " + e.getMessage());
        }
    }
} 