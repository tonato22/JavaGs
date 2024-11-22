package org.example.dtos;

public class PedidoDto {


    private Long clienteid;
    private Long painelSolarid;
    private int quantindadePainelSolar;

    public PedidoDto(Long clienteid, Long painelSolarid, int quantindadePainelSolar) {
        this.clienteid = clienteid;
        this.painelSolarid = painelSolarid;
        this.quantindadePainelSolar = quantindadePainelSolar;

    }

    public PedidoDto() {
    }

    public Long getClienteid() {
        return clienteid;
    }

    public Long getPainelSolarid() {
        return painelSolarid;
    }


    public int getQuantindadePainelSolar() {
        return quantindadePainelSolar;
    }

}

