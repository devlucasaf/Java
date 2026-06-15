package org.application.system.animal.zoologico.ave;

public class Flamingo extends Ave {

    public Flamingo(String nome, int idade) {
        super(nome, idade);
    }

    @Override
    public void emitirSom() {
        System.out.println(nome + " emite sons nasais.");
    }
}
