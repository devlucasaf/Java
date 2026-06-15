package org.application.system.restaurante;

public enum CategoriaItem {
    ENTRADA("Entrada"),
    PRATO_PRINCIPAL("Prato Principal"),
    SOBREMESA("Sobremesa"),
    BEBIDA("Bebida"),
    BEBIDA_ALCOOLICA("Bebida Alcoólica"),
    ACOMPANHAMENTO("Acompanhamento");

    private final String descricao;

    CategoriaItem(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}

