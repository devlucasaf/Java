package org.application.zoologico.ave;

public class Ganso extends Ave {

    public Ganso(String nome, int idade) {
        super(nome, idade);
    }

    public void nadar() {
        System.out.println(nome + " está nadando no lago.");
    }

    @Override
    public void emitirSom() {
        System.out.println(nome + " grasna alto!");
    }
}

