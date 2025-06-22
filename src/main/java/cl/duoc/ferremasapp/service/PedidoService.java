package cl.duoc.ferremasapp.service;

import cl.duoc.ferremasapp.model.Pedido;
import java.util.List;
import java.util.Optional;

public interface PedidoService {
    Pedido crearPedido(Pedido p);
    Optional<Pedido> buscarPorId(Integer id);
    List<Pedido> listarPedidos();
    Pedido actualizarPedido(Pedido p);
    void eliminarPedido(Integer id);
}
