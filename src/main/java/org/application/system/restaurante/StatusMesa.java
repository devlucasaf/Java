package org.application.system.restaurante;

public enum StatusMesa {
    LIVRE("Livre"),
    OCUPADA("Ocupada"),
    RESERVADA("Reservada"),
    EM_LIMPEZA("Em Limpeza");

    private final String descricao;

    StatusMesa(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}

