package org.example.models;

public class Cliente {

    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private long valorContaMensal;

    public Cliente(Long id, String nome, String email, String telefone, long valorContaMensal) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.valorContaMensal = valorContaMensal;
    }

    public Cliente() {

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

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public long getValorContaMensal() {
        return valorContaMensal;
    }


}
