package org.games.detetive;

public class Pista {
    String  descricao;
    boolean verdadeira;

    public Pista(String descricao, boolean verdadeira) {
        this.descricao = descricao;
        this.verdadeira = verdadeira;
    }

    public void mostrar() {
        System.out.println("- " + descricao);
    }
}
