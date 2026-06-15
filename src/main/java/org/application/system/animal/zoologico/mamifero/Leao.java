package org.application.system.animal.zoologico.mamifero;

public class Leao extends Mamifero {

    public Leao(String nome, int idade) {
        super(nome, idade);
    }

    @Override
    public void emitirSom() {
        System.out.println(nome + " ruge!");
    }
}
