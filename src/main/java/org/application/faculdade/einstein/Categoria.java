package org.application.faculdade.einstein;

public enum Categoria {
    NACIONALIDADE("Nacionalidade"),
    COR("Cor"),
    BEBIDA("Bebida"),
    CIGARRO("Cigarro"),
    ANIMAL("Animal");

    private final String nome;

    private Categoria(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}