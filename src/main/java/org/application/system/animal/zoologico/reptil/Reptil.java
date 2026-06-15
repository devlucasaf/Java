package org.application.system.animal.zoologico.reptil;

import org.application.system.animal.zoologico.Animal;

public abstract class Reptil extends Animal {

    public Reptil(String nome, int idade) {
        super(nome, idade);
    }

    public void trocarPele() {
        System.out.println(nome + " está trocando de pele.");
    }

    public void rastejar() {
        System.out.println(nome + " está se locomovendo lentamente.");
    }
}
