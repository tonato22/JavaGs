package org.example.service;

public class ClienteServiceFactory {
    private ClienteServiceFactory() {
    }

    public static ClienteService create(){
        return new ClienteServiceImpl();
    }
}

