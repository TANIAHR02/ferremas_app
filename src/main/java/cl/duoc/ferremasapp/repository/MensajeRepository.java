package cl.duoc.ferremasapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import cl.duoc.ferremasapp.model.Mensaje;

public interface MensajeRepository extends JpaRepository<Mensaje, Long> {}
