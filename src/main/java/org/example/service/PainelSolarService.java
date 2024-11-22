package org.example.service;

import org.example.exceptions.*;
import org.example.models.PainelSolar;

import java.sql.SQLException;
import java.util.List;

public interface PainelSolarService {

    PainelSolar create(PainelSolar painelSolar) throws UnsupportedServiceOperationException, SQLException, PainelSolarNotSavedException;

    List<PainelSolar> findAll();

    PainelSolar update(PainelSolar painelSolar) throws PainelSolarNotFoundException, SQLException;

    void deleteById(Long id) throws PainelSolarNotFoundException, SQLException;

    PainelSolar findById(Long id) throws PainelSolarNotFoundException, SQLException;
}
