package org.example.service;


import org.example.config.DataBaseConnectionFactory;
import org.example.dao.PainelSolarDao;
import org.example.dao.PainelSolarDaoFactory;
import org.example.exceptions.PainelSolarNotFoundException;
import org.example.exceptions.PainelSolarNotSavedException;
import org.example.exceptions.UnsupportedServiceOperationException;
import org.example.models.PainelSolar;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PainelSolarServiceImpl implements PainelSolarService {

    private final PainelSolarDao dao = PainelSolarDaoFactory.create();


    @Override
    public PainelSolar create(PainelSolar painelSolar) throws UnsupportedServiceOperationException, SQLException, PainelSolarNotSavedException {
        if (painelSolar.getId() == null) {
            Connection connection = DataBaseConnectionFactory.create().get();
            try {
                painelSolar = this.dao.save(painelSolar, connection);
                connection.commit();
                return painelSolar;
            } catch (SQLException | PainelSolarNotSavedException e) {
                connection.rollback();
                throw e;
            }
        } else {
            throw new UnsupportedServiceOperationException();
        }
    }


    @Override
    public List<PainelSolar> findAll() {
        return this.dao.findall();
    }

    @Override
    public PainelSolar update(PainelSolar painelSolar) throws PainelSolarNotFoundException, SQLException {
        Connection connection = DataBaseConnectionFactory.create().get();
        painelSolar = this.dao.update(painelSolar, connection);
        connection.commit();
        return painelSolar;
    }

    @Override
    public void deleteById(Long id) throws PainelSolarNotFoundException, SQLException {
        Connection connection = DataBaseConnectionFactory.create().get();
        this.dao.deleteById(id,connection);
        connection.commit();
    }

    @Override
    public PainelSolar findById(Long id) throws PainelSolarNotFoundException, SQLException {
        Connection connection = DataBaseConnectionFactory.create().get();
        PainelSolar painelSolar = this.dao.findById(id,connection);
        connection.commit();
        return painelSolar;
    }
}


