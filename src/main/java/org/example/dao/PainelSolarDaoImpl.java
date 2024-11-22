package org.example.dao;

import oracle.jdbc.internal.OracleTypes;
import org.example.config.DataBaseConnectionFactory;
import org.example.exceptions.PainelSolarNotFoundException;
import org.example.exceptions.PainelSolarNotSavedException;
import org.example.models.PainelSolar;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

class PainelSolarDaoImpl implements PainelSolarDao {

    private final Logger logger = Logger.getLogger(this.getClass().getName());


    @Override
    public PainelSolar save(PainelSolar painelSolar, Connection connection) throws SQLException, PainelSolarNotSavedException {
        final String sql = "BEGIN INSERT INTO PainelSolar(NOME, PRECO, PERCENTUALDEECONOMIA) VALUES ( ?, ?, ?) RETURNING ID INTO ?; END;";
        CallableStatement call = connection.prepareCall(sql);
        call.setString(1, painelSolar.getNome());
        call.setDouble(2, painelSolar.getPreco());
        call.setLong(3, painelSolar.getPercentualDeEconomia());
        call.registerOutParameter(4, OracleTypes.NUMBER);

        int linhasAlteradas = call.executeUpdate();
        long id = call.getLong(4);
        if (linhasAlteradas == 0 || id == 0) {
            throw new PainelSolarNotSavedException();
        }
        painelSolar.setId(id);
        return painelSolar;
    }

    @Override
    public List<PainelSolar> findall() {
        final List<PainelSolar> all = new ArrayList<>();
        final String sql = "SELECT * FROM PAINELSOLAR";
        try (Connection conn = DataBaseConnectionFactory.create().get()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                PainelSolar painelSolar = new PainelSolar(
                        resultSet.getLong("id"),
                        resultSet.getString("nome"),
                        resultSet.getDouble("preco"),
                        resultSet.getLong("percentualDeEconomia"));
                all.add(painelSolar);
            }
        } catch (SQLException e) {
            logger.warning("não foi possível localizar nenhum registro de PainelSolar: " + e.getMessage());
        }
        return all;
    }


    @Override
    public void deleteById(Long id, Connection connection) throws SQLException, PainelSolarNotFoundException {

        final String sql = "delete from painelSolar where id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setLong(1, id);
        int linhasAlteradas = stmt.executeUpdate();
        if (linhasAlteradas == 0) {
            throw new PainelSolarNotFoundException();
        }

    }

    @Override
    public PainelSolar update(PainelSolar painelSolar, Connection connection) throws SQLException, PainelSolarNotFoundException {
        final String sql = "UPDATE painelSolar SET nome = ?, preco = ?, percentualDeEconomia = ? WHERE id = ?";

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1,painelSolar.getNome());
        stmt.setDouble(2, painelSolar.getPreco());
        stmt.setLong(3, painelSolar.getPercentualDeEconomia());
        stmt.setLong(4, painelSolar.getId());
        int linhasAlteradas = stmt.executeUpdate();

        if(linhasAlteradas == 0 ) {
            throw new PainelSolarNotFoundException();
        }
        return painelSolar;

    }

    @Override
    public PainelSolar findById(Long id,Connection connection) throws SQLException, PainelSolarNotFoundException {
        final String sql = "SELECT * FROM painelSolar WHERE id = ?";
        PainelSolar painelSolar = null;
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setLong(1, id);
        ResultSet resultSet = stmt.executeQuery();
        if (resultSet.next()) {
            painelSolar = new PainelSolar(
                    resultSet.getLong("id"),
                    resultSet.getString("nome"),
                    resultSet.getDouble("preco"),
                    resultSet.getLong("percentualDeEconomia"));

        }
        return painelSolar;
    }
}
