package application.system.animal.zoologico.mamifero;

public class Elefante extends Mamifero {

    public Elefante(String nome, int idade) {
        super(nome, idade);
    }

    @Override
    public void emitirSom() {
        System.out.println(getNome() + " faz barulho de trombeta!");
    }
}
