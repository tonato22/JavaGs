package org.example.service;

import org.example.config.DataBaseConnectionFactory;
import org.example.dao.ClienteDao;
import org.example.dao.ClienteDaoFactory;
import org.example.exceptions.ClienteNotFoundException;
import org.example.exceptions.ClienteNotSavedException;
import org.example.exceptions.UnsupportedServiceOperationException;
import org.example.models.Cliente;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

final class ClienteServiceImpl implements ClienteService {

    private final ClienteDao dao = ClienteDaoFactory.create();

    @Override
    public Cliente create(Cliente cliente) throws UnsupportedServiceOperationException, SQLException, ClienteNotSavedException {
        if (cliente.getId() == null) {
            Connection connection = DataBaseConnectionFactory.create().get();
            try {
                cliente = this.dao.save(cliente, connection);
                connection.commit();
                return cliente;
            } catch (SQLException | ClienteNotSavedException e) {
                connection.rollback();
                throw e;
            }
        } else {
            throw new UnsupportedServiceOperationException();
        }

    }

    @Override
    public List<Cliente> findAll() {
        return this.dao.findAll();

    }

    @Override
    public Cliente update(Cliente cliente) throws ClienteNotFoundException, SQLException {
        Connection connection = DataBaseConnectionFactory.create().get();
        cliente = this.dao.update(cliente, connection);
        connection.commit();
        return cliente;
    }

    @Override
    public void deleteById(Long id) throws ClienteNotFoundException, SQLException {
        Connection connection = DataBaseConnectionFactory.create().get();
        this.dao.deleteById(id,connection);
        connection.commit();
    }

    @Override
    public Cliente findById(Long id) throws ClienteNotFoundException, SQLException {
        Connection connection = DataBaseConnectionFactory.create().get();
        Cliente cliente = this.dao.findById(id, connection);
        connection.commit();
        return cliente;
    }
}
