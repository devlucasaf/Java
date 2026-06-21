package application.system.animal.zoologico.mamifero.felino;

import application.system.animal.zoologico.mamifero.Mamifero;

public class Onca extends Mamifero {

    public Onca(String nome, int idade) {
        super(nome, idade);
    }

    @Override
    public void emitirSom() {
        System.out.println(nome + " ruge fortemente!");
    }
}
