package application.system.animal.zoologico.mamifero;

import application.system.animal.zoologico.Animal;

public abstract class Mamifero extends Animal {

    public Mamifero(String nome, int idade) {
        super(nome, idade);
    }

    public void amamentar() {
        System.out.println(nome + " está amamentando.");
    }
}
