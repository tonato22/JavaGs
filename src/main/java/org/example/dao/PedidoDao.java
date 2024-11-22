package org.example.dao;
import org.example.dtos.PedidoDto;
import org.example.exceptions.ClienteNotFoundException;
import org.example.exceptions.PainelSolarNotFoundException;
import org.example.exceptions.PedidoNotFoundException;
import org.example.exceptions.PedidoNotSavedException;
import org.example.models.Pedido;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface PedidoDao {

    Long save(PedidoDto pedidoDto, Connection connection) throws SQLException, PedidoNotSavedException, PainelSolarNotFoundException, ClienteNotFoundException;

    List<Pedido> findall();

    void deleteById(Long id, Connection connection) throws SQLException , PedidoNotFoundException;

    Pedido update(Pedido pedido, Connection connection) throws SQLException , PedidoNotFoundException;

    Pedido findById(Long id, Connection connection) throws SQLException , PedidoNotFoundException;


}
