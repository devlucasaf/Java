package org.application.system.animal.zoologico.reptil;

public class Jacare extends Reptil {

    public Jacare(String nome, int idade) {
        super(nome, idade);
    }

    @Override
    public void emitirSom() {
        System.out.println(nome + " estala a mandíbula!");
    }
}
