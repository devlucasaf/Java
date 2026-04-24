package org.application.zoologico.ave;

public class Ema extends Ave {

    public Ema(String nome, int idade) {
        super(nome, idade);
    }

    @Override
    public void emitirSom() {
        System.out.println(nome + " emite um som grave.");
    }
}
