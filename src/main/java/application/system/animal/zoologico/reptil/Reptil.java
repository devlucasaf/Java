package application.system.animal.zoologico.reptil;

import application.system.animal.zoologico.Animal;
import application.system.animal.zoologico.Sexo;

public abstract class Reptil extends Animal {

    private final boolean peconhento;

    public Reptil(String nome, int idade) {
        this(nome, idade, Sexo.MACHO, false);
    }

    public Reptil(String nome, int idade, Sexo sexo, boolean peconhento) {
        super(nome, idade, sexo);
        this.peconhento = peconhento;
    }

    public void trocarPele() {
        System.out.println(getNome() + " está trocando de pele.");
    }

    public void rastejar() {
        System.out.println(getNome() + " está se locomovendo lentamente.");
    }

    public boolean isPeconhento() {
        return peconhento;
    }
}
