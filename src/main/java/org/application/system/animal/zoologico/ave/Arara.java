package org.application.system.animal.zoologico.ave;

public class Arara extends Ave {

    public Arara(String nome, int idade) {
        super(nome, idade);
    }

    @Override
    public void emitirSom() {
        System.out.println(nome + " grita!");
    }
}
