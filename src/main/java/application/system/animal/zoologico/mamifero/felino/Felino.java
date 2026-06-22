package application.system.animal.zoologico.mamifero.felino;

import application.system.animal.zoologico.Sexo;
import application.system.animal.zoologico.mamifero.Mamifero;

public abstract class Felino extends Mamifero {

    public Felino(String nome, int idade) {
        super(nome, idade);
    }

    public Felino(String nome, int idade, Sexo sexo) {
        super(nome, idade, sexo);
    }

    public void cacar() {
        System.out.println(getNome() + " esta caçando silenciosamente.");
    }

    public void afiarGarras() {
        System.out.println(getNome() + " esta afiando as garras.");
    }
}

