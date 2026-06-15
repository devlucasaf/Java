package org.application.system.animal.petshop;

import java.time.LocalDate;

public class Cachorro extends Animal {
    private boolean adestrado;
    private String  nivelEnergia;

    public Cachorro(String nome, String raca, LocalDate dataNascimento, PorteAnimal porte,
                    double peso, String cor, boolean adestrado, String nivelEnergia) {
        super(nome, raca, dataNascimento, TipoAnimal.CACHORRO, porte, peso, cor);
        this.adestrado = adestrado;
        this.nivelEnergia = nivelEnergia;
    }

    @Override
    public void emitirSom() {
        System.out.println(nome + " late: Au au!");
    }

    public void brincar() {
        System.out.println(nome + " está brincando com a bola.");
    }

    @Override
    public void exibirInformacoes() {
        super.exibirInformacoes();
        System.out.println("Adestrado: " + (adestrado ? "Sim" : "Não"));
        System.out.println("Nível de energia: " + nivelEnergia);
    }
}
