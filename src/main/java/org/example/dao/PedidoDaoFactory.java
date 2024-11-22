package org.example.dao;

public class PedidoDaoFactory {

    private PedidoDaoFactory() {

    }
    public static PedidoDao create(){
        return new PedidoDaoImpl();
    }
}
