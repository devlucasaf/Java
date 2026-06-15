package org.application.system.animal.zoologico.reptil;

public class Calango extends Reptil {

    public Calango(String nome, int idade) {
        super(nome, idade);
    }

    public void correr() {
        System.out.println(nome + " corre rapidamente pelas pedras.");
    }

    @Override
    public void emitirSom() {
        System.out.println(nome + " faz um leve chiado.");
    }
}
