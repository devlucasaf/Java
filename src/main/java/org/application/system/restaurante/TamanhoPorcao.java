package org.application.system.restaurante;

public enum TamanhoPorcao {
    PEQUENA("Pequena", 0.7),
    MEDIA("Média", 1.0),
    GRANDE("Grande", 1.4),
    FAMILIA("Família", 2.0);

    private final String descricao;
    private final double multiplicador;

    TamanhoPorcao(String descricao, double multiplicador) {
        this.descricao = descricao;
        this.multiplicador = multiplicador;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getMultiplicador() {
        return multiplicador;
    }
}

