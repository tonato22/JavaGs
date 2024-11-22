package org.example.service;

import org.example.dtos.PedidoDto;
import org.example.exceptions.*;
import org.example.models.Pedido;
import java.sql.SQLException;
import java.util.List;

public interface PedidoService {


    List<Pedido> findAll();

    Pedido create(PedidoDto pedidoDto) throws UnsupportedServiceOperationException, SQLException, PedidoNotSavedException, PainelSolarNotFoundException, ClienteNotFoundException, PedidoNotFoundException;

    Pedido update(Pedido pedido) throws PedidoNotFoundException, SQLException;

    void deleteById(Long id) throws PedidoNotFoundException, SQLException;




}
