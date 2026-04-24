package org.application.zoologico.mamifero;

public class Jaguatirica extends Mamifero {

    public Jaguatirica(String nome, int idade) {
        super(nome, idade);
    }

    @Override
    public void emitirSom() {
        System.out.println(nome + " emite um miado forte!");
    }
}
