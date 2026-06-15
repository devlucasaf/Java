package org.application.system.animal.zoologico.reptil;

public class Iguana extends Reptil {

    public Iguana(String nome, int idade) {
        super(nome, idade);
    }

    public void subirArvore() {
        System.out.println(nome + " está subindo em uma árvore.");
    }

    @Override
    public void emitirSom() {
        System.out.println(nome + " faz um som grave ao se sentir ameaçada.");
    }
}
