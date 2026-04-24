package org.application.zoologico.ave;

import org.application.zoologico.Animal;

public abstract class Ave extends Animal {

    public Ave(String nome, int idade) {
        super(nome, idade);
    }

    public void voar() {
        System.out.println(nome + " está voando.");
    }
}
