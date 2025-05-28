package cl.duoc.ferremasapp.service.impl;

import cl.duoc.ferremasapp.model.Pedido;
import cl.duoc.ferremasapp.repository.PedidoRepository;
import cl.duoc.ferremasapp.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Override
    public Pedido crearPedido(Pedido p) {
        return pedidoRepository.save(p);
    }

    @Override
    public Optional<Pedido> buscarPorId(Long id) {
        return pedidoRepository.findById(id);
    }

    @Override
    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }

    @Override
    public Pedido actualizarPedido(Pedido p) {
        return pedidoRepository.save(p);
    }

    @Override
    public void eliminarPedido(Long id) {
        pedidoRepository.deleteById(id);
    }
}
