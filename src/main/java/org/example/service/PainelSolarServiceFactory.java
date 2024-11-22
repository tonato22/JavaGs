package org.example.service;

public class PainelSolarServiceFactory {

    private PainelSolarServiceFactory() {
    }

    public static PainelSolarService create(){
        return new PainelSolarServiceImpl();
    }
}

