package org.application.system.animal.zoologico.mamifero;

public class Girafa extends Mamifero {

    public Girafa(String nome, int idade) {
        super(nome, idade);
    }

    @Override
    public void emitirSom() {
        System.out.println(nome + " emite sons quase inaudíveis.");
    }
}
