package application.system.animal.zoologico.mamifero;

import application.system.animal.zoologico.Animal;
import application.system.animal.zoologico.Sexo;

public abstract class Mamifero extends Animal {

    public Mamifero(String nome, int idade) {
        super(nome, idade);
    }

    public Mamifero(String nome, int idade, Sexo sexo) {
        super(nome, idade, sexo);
    }

    public void amamentar() {
        if (getSexo() == Sexo.FEMEA) {
            System.out.println(getNome() + " esta amamentando.");
        } else {
            System.out.println(getNome() + " nao pode amamentar.");
        }
    }
}
