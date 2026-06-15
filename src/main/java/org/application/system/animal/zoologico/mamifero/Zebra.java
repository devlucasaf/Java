package org.application.system.animal.zoologico.mamifero;

public class Zebra extends Mamifero {

    public Zebra(String nome, int idade) {
        super(nome, idade);
    }

    @Override
    public void emitirSom() {
        System.out.println(nome + " relincha!");
    }
}

