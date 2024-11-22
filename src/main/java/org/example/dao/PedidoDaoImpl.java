package org.example.dao;

import oracle.jdbc.internal.OracleTypes;
import org.example.config.DataBaseConnectionFactory;
import org.example.dtos.PedidoDto;
import org.example.exceptions.ClienteNotFoundException;
import org.example.exceptions.PainelSolarNotFoundException;
import org.example.exceptions.PedidoNotFoundException;
import org.example.exceptions.PedidoNotSavedException;
import org.example.models.Cliente;
import org.example.models.PainelSolar;
import org.example.models.Pedido;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

class PedidoDaoImpl implements PedidoDao {

    private final Logger logger = Logger.getLogger(this.getClass().getName());



    @Override
    public Long save(PedidoDto pedidoDto, Connection connection) throws SQLException, PedidoNotSavedException, PainelSolarNotFoundException, ClienteNotFoundException {
        final String sql = """
                BEGIN
                    INSERT INTO PEDIDO (CLIENTE_ID, PAINEL_SOLAR_ID, QUANTIDADE_PAINEL_SOLAR)
                    VALUES (?, ?, ?)
                    RETURNING ID INTO ?;
                END;
                """;

        CallableStatement call = connection.prepareCall(sql);

        call.setLong(1,pedidoDto.getClienteid());
        call.setLong(2, pedidoDto.getPainelSolarid());
        call.setLong(3, pedidoDto.getQuantindadePainelSolar());
        call.registerOutParameter(4, OracleTypes.NUMBER);

        int linhasAlteradas = call.executeUpdate();
        long id = call.getLong(4);

        if (linhasAlteradas == 0 || id == 0) {
            throw new PedidoNotSavedException();
        }

      return id;

    }

    @Override
    public List<Pedido> findall() {
        final List<Pedido> all = new ArrayList<>();
        final String sql = "SELECT p.id, p.cliente_id AS clienteId," +
                " p.painel_solar_id AS painelSolarId," +
                " p.data_pedido AS dataPedido," +
                " p.quantidade_painel_solar AS quantidadePainelSolar," +
                " c.id AS clienteId, c.nome AS clienteNome," +
                " c.valorContaMensal AS clienteContaMensal," +
                " c.email AS clienteEmail," +
                " c.telefone AS clienteTelefone," +
                " ps.id AS painelSolarId," +
                " ps.percentualDeEconomia AS percentualDeEconomia," +
                " ps.nome AS painelSolarNome," +
                " ps.preco AS painelValor" +
                " FROM Pedido p" +
                " JOIN Cliente c ON p.cliente_id = c.id" +
                " JOIN PainelSolar ps ON p.painel_solar_id = ps.id";


        try (Connection conn = DataBaseConnectionFactory.create().get()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                Cliente cliente = new Cliente(
                        resultSet.getLong("clienteId"),
                        resultSet.getString("clienteNome"),
                        resultSet.getString("clienteEmail"),
                        resultSet.getString("clienteTelefone"),
                        resultSet.getLong("clienteContaMensal")
                );


                PainelSolar painelSolar = new PainelSolar(
                        resultSet.getLong("painelSolarId"),
                        resultSet.getString("painelSolarNome"),
                        resultSet.getDouble("painelValor"),
                        resultSet.getLong("percentualDeEconomia")
                );


                Pedido pedido = new Pedido(
                        resultSet.getDate("dataPedido"),
                        resultSet.getLong("id"),
                        cliente,
                        painelSolar,
                        resultSet.getInt("quantidadePainelSolar")
                );

                all.add(pedido);
            }
        } catch (SQLException e) {
            logger.warning("Não foi possível localizar nenhum registro de Pedido: " + e.getMessage());
            e.printStackTrace();
        }

        return all;
    }


    @Override
    public void deleteById(Long id, Connection connection) throws SQLException, PedidoNotFoundException {
        final String sql = "delete from pedido where id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setLong(1, id);
        int linhasAlteradas = stmt.executeUpdate();
        if (linhasAlteradas == 0) {
            throw new PedidoNotFoundException();
        }
    }

    @Override
    public Pedido update(Pedido pedido, Connection connection) throws SQLException, PedidoNotFoundException {
        final String sql = """
                UPDATE pedido 
                SET cliente_id = ?, 
                    painel_solar_id = ?, 
                    data_pedido = ?, 
                    valor_total = ?, 
                    quantidade_painel_solar = ?, 
                    valor_total_economia = ? 
                WHERE id = ?
                """;

        PreparedStatement stmt = connection.prepareStatement(sql);

        stmt.setLong(1, pedido.getCliente().getId());
        stmt.setLong(2, pedido.getPainelSolar().getId());
        stmt.setDouble(3, pedido.getValorTotal());
        stmt.setLong(4, pedido.getQuantindadePainelSolar());
        stmt.setLong(6, pedido.getId());

        int linhasAlteradas = stmt.executeUpdate();

        if (linhasAlteradas == 0) {
            throw new PedidoNotFoundException();
        }

        return pedido;
    }

    @Override
    public Pedido findById(Long id, Connection connection) throws SQLException, PedidoNotFoundException {
        final String sql = "SELECT p.id, p.cliente_id AS clienteId," +
                " p.painel_solar_id AS painelSolarId," +
                " p.data_pedido AS dataPedido," +
                " p.quantidade_painel_solar AS quantidadePainelSolar," +
                " c.id AS clienteId, c.nome AS clienteNome," +
                " c.valorContaMensal AS clienteContaMensal," +
                " c.email AS clienteEmail," +
                " c.telefone AS clienteTelefone," +
                " ps.id AS painelSolarId," +
                " ps.percentualDeEconomia AS percentualDeEconomia," +
                " ps.nome AS painelSolarNome," +
                " ps.preco AS painelValor" +
                " FROM Pedido p" +
                " JOIN Cliente c ON p.cliente_id = c.id" +
                " JOIN PainelSolar ps ON p.painel_solar_id = ps.id" +
                " WHERE p.id = ?";

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setLong(1, id);
        ResultSet resultSet = stmt.executeQuery();

        while (resultSet.next()) {
            Cliente cliente = new Cliente(
                    resultSet.getLong("clienteId"),
                    resultSet.getString("clienteNome"),
                    resultSet.getString("clienteEmail"),
                    resultSet.getString("clienteTelefone"),
                    resultSet.getLong("clienteContaMensal")
            );


            PainelSolar painelSolar = new PainelSolar(
                    resultSet.getLong("painelSolarId"),
                    resultSet.getString("painelSolarNome"),
                    resultSet.getDouble("painelValor"),
                    resultSet.getLong("percentualDeEconomia")
            );


            Pedido pedido = new Pedido(
                    resultSet.getDate("dataPedido"),
                    resultSet.getLong("id"),
                    cliente,
                    painelSolar,
                    resultSet.getInt("quantidadePainelSolar")
            );

            return pedido;
        }
        throw new PedidoNotFoundException();
    }

}


