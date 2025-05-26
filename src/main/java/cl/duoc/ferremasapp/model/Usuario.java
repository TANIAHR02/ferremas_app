package cl.duoc.ferremasapp.model;

import javax.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @Column(nullable = false, length = 100)
    private String apellido;
    
    @Column(nullable = false, length = 100, unique = true)
    private String email;
    
    @Column(nullable = false, length = 255)
    private String password;
    
    @Column(length = 20)
    private String telefono;
    
    @Column(length = 255)
    private String direccion;
    
    @ManyToOne
    @JoinColumn(name = "rol_id")
    private Rol rol;
    
    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;
    
    @Column(name = "ultimo_login")
    private LocalDateTime ultimoLogin;
    
    @Column
    private Boolean estado;
    
    @PrePersist
    public void prePersist() {
        fechaRegistro = LocalDateTime.now();
        estado = true;
    }
}