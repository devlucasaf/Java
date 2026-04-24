package org.application.zoologico.reptil;

public class Camaleao extends Reptil {

    public Camaleao(String nome, int idade) {
        super(nome, idade);
    }

    public void mudarCor() {
        System.out.println(nome + " está mudando de cor para se camuflar.");
    }

    @Override
    public void emitirSom() {
        System.out.println(nome + " emite um som sutil.");
    }
}

