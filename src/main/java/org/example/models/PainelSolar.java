package org.example.models;

public class PainelSolar {

    private Long id;

    private String nome;

    private double preco;

    private Long percentualDeEconomia;

    public PainelSolar(Long id, String nome, double preco, Long percentualDeEconomia) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.percentualDeEconomia = percentualDeEconomia;
    }
    public PainelSolar() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }



    public double getPreco() {
        return preco;
    }



    public Long getPercentualDeEconomia() {
        return percentualDeEconomia;
    }

}
