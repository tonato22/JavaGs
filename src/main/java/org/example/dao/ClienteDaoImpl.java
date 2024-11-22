package org.example.dao;

import oracle.jdbc.internal.OracleTypes;
import org.example.config.DataBaseConnectionFactory;
import org.example.exceptions.ClienteNotFoundException;
import org.example.exceptions.ClienteNotSavedException;
import org.example.models.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

class ClienteDaoImpl implements ClienteDao {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public List<Cliente> findAll() {
        final List<Cliente> all = new ArrayList<>();
        final String sql = "SELECT * FROM CLIENTE";
        try (Connection conn = DataBaseConnectionFactory.create().get()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Cliente cliente = new Cliente(
                        resultSet.getLong("id"),
                        resultSet.getString("nome"),
                        resultSet.getString("email"),
                        resultSet.getString("telefone"),
                        resultSet.getLong("valorContaMensal"));
                all.add(cliente);
            }
        } catch (SQLException e) {
            logger.warning("não foi possível localizar nenhum registro de Cliente: " + e.getMessage());
        }
        return all;
    }


    @Override
    public void deleteById(Long id, Connection connection) throws ClienteNotFoundException, SQLException {
        final String sql = "delete from cliente where id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setLong(1, id);
        int linhasAlteradas = stmt.executeUpdate();
        if (linhasAlteradas == 0) {
            throw new ClienteNotFoundException();
        }
    }

    @Override
    public Cliente save(Cliente cliente, Connection connection) throws SQLException, ClienteNotSavedException {
        final String sql = "BEGIN INSERT INTO CLIENTE(NOME, EMAIL, TELEFONE, VALORCONTAMENSAL) VALUES (?, ?, ?, ?) RETURNING ID INTO ?; END;";
        CallableStatement call = connection.prepareCall(sql);
        call.setString(1, cliente.getNome());
        call.setString(2, cliente.getEmail());
        call.setString(3, cliente.getTelefone());
        call.setLong(4, cliente.getValorContaMensal() );
        call.registerOutParameter(5 , OracleTypes.NUMBER);

        int linhasAlteradas = call.executeUpdate();
        long id = call.getLong(5);
        if (linhasAlteradas == 0 || id == 0) {
            throw new ClienteNotSavedException();
        }
        cliente.setId(id);
        return cliente;
    }

    @Override
    public Cliente update(Cliente cliente, Connection connection) throws ClienteNotFoundException, SQLException {
        final String sql = "UPDATE cliente SET nome = ?, email = ?, telefone = ?, valor_conta_mensal = ? WHERE id = ?";

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1,cliente.getNome());
        stmt.setString(2, cliente.getEmail());
        stmt.setString(3, cliente.getTelefone());
        stmt.setLong(4, cliente.getValorContaMensal());
        stmt.setLong(5, cliente.getId());
        int linhasAlteradas = stmt.executeUpdate();

        if(linhasAlteradas == 0 ) {
            throw new ClienteNotFoundException();
        }
        return cliente;
    }

    @Override
    public Cliente findById(Long id, Connection connection) throws ClienteNotFoundException, SQLException {
        final String sql = "SELECT * FROM cliente WHERE id = ?";
        Cliente cliente = null;
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setLong(1, id);
        ResultSet resultSet = stmt.executeQuery();
        if (resultSet.next()) {
             cliente = new Cliente(
                    resultSet.getLong("id"),
                    resultSet.getString("nome"),
                    resultSet.getString("email"),
                    resultSet.getString("telefone"),
                    resultSet.getLong("valorContaMensal"));


        }
        return cliente;
    }
}

