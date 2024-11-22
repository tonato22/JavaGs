package org.example.service;

import org.example.exceptions.ClienteNotFoundException;
import org.example.exceptions.ClienteNotSavedException;
import org.example.exceptions.UnsupportedServiceOperationException;
import org.example.models.Cliente;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ClienteService {


    Cliente create(Cliente cliente) throws UnsupportedServiceOperationException, SQLException, ClienteNotSavedException;

    List<Cliente> findAll();

   Cliente update(Cliente cliente) throws ClienteNotFoundException, SQLException;

    void deleteById(Long id) throws ClienteNotFoundException, SQLException;

    Cliente findById(Long id) throws ClienteNotFoundException, SQLException;

}

