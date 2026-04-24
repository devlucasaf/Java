package org.application.zoologico.mamifero;

public class Ariranha extends Mamifero {

    public Ariranha(String nome, int idade) {
        super(nome, idade);
    }

    @Override
    public void emitirSom() {
        System.out.println(nome + " emite sons agudos!");
    }
}
