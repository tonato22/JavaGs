package org.example.service;

import org.example.config.DataBaseConnectionFactory;
import org.example.dao.PedidoDao;
import org.example.dao.PedidoDaoFactory;
import org.example.dtos.PedidoDto;
import org.example.exceptions.*;
import org.example.models.Pedido;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PedidoServiceImpl implements PedidoService {
    private final PedidoDao dao = PedidoDaoFactory.create();


    @Override
    public List<Pedido> findAll() {
        return this.dao.findall();
    }

    @Override
    public Pedido create(PedidoDto pedidoDto) throws UnsupportedServiceOperationException, SQLException, PedidoNotSavedException, PainelSolarNotFoundException, ClienteNotFoundException, PedidoNotFoundException {
        if (pedidoDto.getClienteid() != null) {
            Connection connection = DataBaseConnectionFactory.create().get();
            try {
                Long id = this.dao.save(pedidoDto, connection);
                Pedido pedido = this.dao.findById(id,connection);
                connection.commit();
                return pedido;
            } catch (SQLException | PedidoNotSavedException | PainelSolarNotFoundException | ClienteNotFoundException | PedidoNotFoundException e) {
                connection.rollback();
                throw e;
            }
        } else {
            throw new UnsupportedServiceOperationException();
        }

    }

    @Override
    public Pedido update(Pedido pedido) throws PedidoNotFoundException, SQLException {
        Connection connection = DataBaseConnectionFactory.create().get();
        pedido = this.dao.update(pedido, connection);
        connection.commit();
        return pedido;
    }

    @Override
    public void deleteById(Long id) throws PedidoNotFoundException, SQLException {
        Connection connection = DataBaseConnectionFactory.create().get();
        this.dao.deleteById(id, connection);
        connection.commit();
    }
}
