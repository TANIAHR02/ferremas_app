package cl.duoc.ferremasapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.ferremasapp.model.Mensaje;

public interface MensajeRepository extends JpaRepository<Mensaje, Integer> {

    List<Mensaje> findByRemitente_Id(int id);

    List<Mensaje> findByDestinatario_Id(int id);
}
