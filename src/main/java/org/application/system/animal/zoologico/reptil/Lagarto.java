package org.application.system.animal.zoologico.reptil;

public class Lagarto extends Reptil {

    public Lagarto(String nome, int idade) {
        super(nome, idade);
    }

    public void tomarSol() {
        System.out.println(nome + " está tomando sol para regular a temperatura do corpo.");
    }

    @Override
    public void emitirSom() {
        System.out.println(nome + " emite um som baixo.");
    }
}
