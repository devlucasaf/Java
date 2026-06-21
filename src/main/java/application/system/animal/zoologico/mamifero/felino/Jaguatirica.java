package application.system.animal.zoologico.mamifero.felino;

import application.system.animal.zoologico.mamifero.Mamifero;

public class Jaguatirica extends Mamifero {

    public Jaguatirica(String nome, int idade) {
        super(nome, idade);
    }

    @Override
    public void emitirSom() {
        System.out.println(nome + " emite um miado forte!");
    }
}
