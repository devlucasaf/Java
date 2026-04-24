package org.application.zoologico.ave;

public class Pavao extends Ave {

    public Pavao(String nome, int idade) {
        super(nome, idade);
    }

    public void abrirCauda() {
        System.out.println(nome + " abre sua linda cauda colorida.");
    }

    @Override
    public void emitirSom() {
        System.out.println(nome + " emite um chamado alto.");
    }
}