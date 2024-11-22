package org.example.dao;

public class PainelSolarDaoFactory {

    private PainelSolarDaoFactory() {
    }

    public static PainelSolarDao create(){return new PainelSolarDaoImpl();
    }
}
