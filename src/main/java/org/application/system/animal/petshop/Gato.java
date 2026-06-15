package org.application.system.animal.petshop;

import java.time.LocalDate;

public class Gato extends Animal {
    private boolean gostaDeArranhador;
    private boolean independente;

    public Gato(String nome, String raca, LocalDate dataNascimento, PorteAnimal porte,
                double peso, String cor, boolean gostaDeArranhador, boolean independente) {
        super(nome, raca, dataNascimento, TipoAnimal.GATO, porte, peso, cor);
        this.gostaDeArranhador = gostaDeArranhador;
        this.independente = independente;
    }

    @Override
    public void emitirSom() {
        System.out.println(nome + " mia: Miau!");
    }

    public void arranhar() {
        System.out.println(nome + " está arranhando o sofá...");
    }

    @Override
    public void exibirInformacoes() {
        super.exibirInformacoes();
        System.out.println("Gosta de arranhador: " + (gostaDeArranhador ? "Sim" : "Não"));
        System.out.println("Independente: " + (independente ? "Sim" : "Não"));
    }
}