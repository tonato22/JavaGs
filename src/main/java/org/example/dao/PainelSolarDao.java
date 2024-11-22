package org.example.dao;

import org.example.exceptions.PainelSolarNotFoundException;
import org.example.exceptions.PainelSolarNotSavedException;
import org.example.models.PainelSolar;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface PainelSolarDao {

    PainelSolar save(PainelSolar painelSolar, Connection connection) throws SQLException, PainelSolarNotSavedException;

    List<PainelSolar> findall();

    void deleteById(Long id, Connection connection) throws SQLException , PainelSolarNotFoundException;

    PainelSolar update(PainelSolar painelSolar, Connection connection) throws SQLException , PainelSolarNotFoundException;

    PainelSolar findById(Long id, Connection connection) throws SQLException , PainelSolarNotFoundException;
}
