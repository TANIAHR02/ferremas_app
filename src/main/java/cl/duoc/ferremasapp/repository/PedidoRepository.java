package cl.duoc.ferremasapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import cl.duoc.ferremasapp.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {}
