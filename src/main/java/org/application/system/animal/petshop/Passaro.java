package org.application.system.animal.petshop;

import java.time.LocalDate;

public class Passaro extends Animal {
    private boolean sabeCantar;
    private String  corPenas;

    public Passaro(String nome, String raca, LocalDate dataNascimento, PorteAnimal porte,
                   double peso, String cor, boolean sabeCantar, String corPenas) {
        super(nome, raca, dataNascimento, TipoAnimal.PASSARO, porte, peso, cor);
        this.sabeCantar = sabeCantar;
        this.corPenas = corPenas;
    }

    @Override
    public void emitirSom() {
        if (sabeCantar) {
            System.out.println(nome + " canta: Fiu fiu!");
        } else {
            System.out.println(nome + " piou: Piu piu.");
        }
    }

    public void voar() {
        System.out.println(nome + " está voando pela loja.");
    }

    @Override
    public void exibirInformacoes() {
        super.exibirInformacoes();
        System.out.println("Sabe cantar: " + (sabeCantar ? "Sim" : "Não"));
        System.out.println("Cor das penas: " + corPenas);
    }
}