package org.application.system.animal.zoologico.mamifero;

public class Anta extends Mamifero {

    public Anta(String nome, int idade) {
        super(nome, idade);
    }

    @Override
    public void emitirSom() {
        System.out.println(nome + " resmunga.");
    }
}
