package org.example.dtos;

public class ClienteDto {

    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private long valorContaMensal;

    public ClienteDto(Long id, String nome, String email, String telefone, long valorContaMensal) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.valorContaMensal = valorContaMensal;
    }

    public ClienteDto (){
    }

    public Long getId() {
        return id;
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
