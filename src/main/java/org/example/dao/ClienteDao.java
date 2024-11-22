package org.example.dao;

import org.example.exceptions.ClienteNotFoundException;
import org.example.exceptions.ClienteNotSavedException;
import org.example.models.Cliente;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ClienteDao  {
    List<Cliente> findAll();

    void deleteById(Long id, Connection connection) throws ClienteNotFoundException, SQLException;

    Cliente save(Cliente cliente,Connection connection) throws SQLException, ClienteNotSavedException;

    Cliente update(Cliente cliente, Connection connection) throws ClienteNotFoundException, SQLException;

    Cliente findById(Long id, Connection connection) throws ClienteNotFoundException, SQLException;
}
