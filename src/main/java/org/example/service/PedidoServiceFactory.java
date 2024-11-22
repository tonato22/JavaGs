package org.example.service;

public class PedidoServiceFactory {

    private PedidoServiceFactory() {

    }
    public static PedidoService create(){
        return new PedidoServiceImpl();
    }
}
