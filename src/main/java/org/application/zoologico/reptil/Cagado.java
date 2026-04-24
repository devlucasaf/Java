package org.application.zoologico.reptil;

public class Cagado extends Reptil {

    public Cagado(String nome, int idade) {
        super(nome, idade);
    }

    @Override
    public void emitirSom() {
        System.out.println(nome + " faz um som baixo.");
    }
}
