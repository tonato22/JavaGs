package org.example.models;


import java.text.SimpleDateFormat;
import java.util.Date;

public class Pedido {
    private Long id;
    private Cliente cliente;
    private PainelSolar painelSolar;
    private int quantindadePainelSolar;
    private Date data;

    public Pedido(Long id, Cliente cliente, PainelSolar painelSolar, int quantindadePainelSolar) {
        this.id = id;
        this.cliente = cliente;
        this.painelSolar = painelSolar;
        this.quantindadePainelSolar = quantindadePainelSolar;
    }

    public Pedido(Date data, Long id, Cliente cliente, PainelSolar painelSolar, int quantindadePainelSolar) {
        this.data = data;
        this.id = id;
        this.cliente = cliente;
        this.painelSolar = painelSolar;
        this.quantindadePainelSolar = quantindadePainelSolar;
    }

    public String getData() {
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(data);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }



    public PainelSolar getPainelSolar() {
        return painelSolar;
    }




    public double getValorTotal() {
        return painelSolar.getPreco()*quantindadePainelSolar;
    }




    public int getQuantindadePainelSolar() {
        return quantindadePainelSolar;
    }

    public void setQuantindadePainelSolar(int quantindadePainelSolar) {
        this.quantindadePainelSolar = quantindadePainelSolar;
    }

    public double getPercentualEconomia(){
        return this.cliente.getValorContaMensal() * this.painelSolar.getPercentualDeEconomia() /100;
    }


}
