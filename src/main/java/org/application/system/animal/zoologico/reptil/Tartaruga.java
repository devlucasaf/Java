package org.application.system.animal.zoologico.reptil;

public class Tartaruga extends Reptil {

    public Tartaruga(String nome, int idade) {
        super(nome, idade);
    }

    @Override
    public void emitirSom() {
        System.out.println(nome + " faz sons quase imperceptíveis.");
    }
}

