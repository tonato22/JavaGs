package org.example.dtos;

public class PainelSolarDto {


    private Long id;

    private String nome;

    private double preco;

    private Long percentualDeEconomia;

    public PainelSolarDto(Long id, String nome, double preco, Long percentualDeEconomia) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.percentualDeEconomia = percentualDeEconomia;
    }

    public PainelSolarDto() {
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

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public Long getPercentualDeEconomia() {
        return percentualDeEconomia;
    }

    public void setPercentualDeEconomia(Long percentualDeEconomia) {
        this.percentualDeEconomia = percentualDeEconomia;
    }
}
