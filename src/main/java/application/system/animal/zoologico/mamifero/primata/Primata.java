package application.system.animal.zoologico.mamifero.primata;

import application.system.animal.zoologico.Sexo;
import application.system.animal.zoologico.mamifero.Mamifero;

public abstract class Primata extends Mamifero {

    public Primata(String nome, int idade) {
        super(nome, idade);
    }

    public Primata(String nome, int idade, Sexo sexo) {
        super(nome, idade, sexo);
    }

    public void subirArvore() {
        System.out.println(getNome() + " esta subindo na arvore.");
    }

    public void usarFerramenta() {
        System.out.println(getNome() + " esta usando uma ferramenta.");
    }
}

