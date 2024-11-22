package org.example.dao;

public class ClienteDaoFactory {

    private ClienteDaoFactory() {
    }

    public static ClienteDao create(){
        return new ClienteDaoImpl();
    }
}

